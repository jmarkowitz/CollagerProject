package controller.file;

import static model.Project.MAX_VALUE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModelState;


public class PPMHandler extends AbstractFileHandler<PixelInterface[][]> {

  public PPMHandler(ProjectModelState model) {
    super(model);
  }

  @Override
  public PixelInterface[][] readFile(String filepath) throws IOException {
    PixelInterface[][] pixels;
    pixels = this.readPixelsFromPPM(filepath);
    return this.resizePixelArray(pixels, this.projectHeight, this.projectWidth);
  }

  @Override
  public void writeFile(String filepath) throws IOException {
    PixelInterface[][] finalImage = this.compressLayers();//TODO should we have a method in project that does this?
    this.writePixelsToPPM(finalImage, filepath);
  }

  private PixelInterface[][] readPixelsFromPPM(String filepath) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filepath));
    // Read in the header information
    String line = reader.readLine();
    if (!line.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: " + filepath);
    }
    int width = 0, height = 0, maxval = 0;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith("#")) {
        continue; // Ignore comments
      }
      if (width == 0) {
        String[] tokens = line.trim().split("\\s+");
        width = Integer.parseInt(tokens[0]);
        height = Integer.parseInt(tokens[1]);
        maxval = Integer.parseInt(tokens[2]);
      } else {
        break;
      }
    }
    // Read in the pixel data
    PixelInterface[][] pixels = new Pixel[this.projectHeight][this.projectWidth];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = Integer.parseInt(reader.readLine().trim());
        int green = Integer.parseInt(reader.readLine().trim());
        int blue = Integer.parseInt(reader.readLine().trim());
        pixels[height][width] = new Pixel(this.scalePixel(red, maxval),
            this.scalePixel(green, maxval), this.scalePixel(blue, maxval), 255);
      }
    }
    reader.close();
    return pixels;
  }

  private void writePixelsToPPM(PixelInterface[][] compressedImage, String filepath)
      throws IOException {
    StringBuilder finalImage = new StringBuilder();
    finalImage.append("P3").append(System.lineSeparator());
    finalImage.append(this.projectWidth).append(" ").append(this.projectHeight).append(System.lineSeparator());
    finalImage.append(MAX_VALUE).append(System.lineSeparator());
    for (int row = 0; row < this.projectHeight; row++) {
      for (int col = 0; col < this.projectWidth; col++) {
        String curPixel = compressedImage[row][col].toString(1);
        finalImage.append(curPixel).append(" ");
      }
    }
    String workingDir = System.getProperty("user.dir");
    String absoluteFilePath = workingDir + File.separator + filepath;
    FileWriter fileWriter;
    fileWriter = new FileWriter(absoluteFilePath);
    fileWriter.write(finalImage.toString());
    fileWriter.close();
  }
}

