package controller.file;

import static model.Project.MAX_VALUE;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModelState;

/**
 * Represents a handler for PPM related image files. Allows the user to read and write to PPM
 * files.
 */
public class PPMHandler extends AbstractFileHandler<PixelInterface[][]> {

  public PPMHandler(ProjectModelState modelState) {
    super(modelState);
  }

  /**
   * This method get all the information in a file.
   *
   * @param filepath The filepath of a file.
   * @return All the information in a file.
   * @throws IOException if the file is invalid.
   */
  @Override
  public PixelInterface[][] readFile(String filepath) throws IOException {
    int width;
    int height;
    PixelInterface[][] imageLayer;
    Scanner sc = new Scanner(FileUtil.readFileAsString(filepath));
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IOException("Not a PPM file");
    }
    width = sc.nextInt();
    height = sc.nextInt();
    imageLayer = new PixelInterface[height][width];
    int maxValue = sc.nextInt();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        imageLayer[row][col] = new Pixel(scalePixel(red, maxValue),
            scalePixel(green, maxValue),
            scalePixel(blue, maxValue),
            255);
      }
    }
    return imageLayer.clone();
  }

  /**
   * This method read the information to a file.
   *
   * @param filepath the filepath of a file.
   * @throws IOException if the file is invalid.
   */
  @Override
  public void writeFile(String filepath) throws IOException {
    PixelInterface[][] finalImage = this.modelState.compressLayers();
    this.writePixelsToPPM(finalImage, filepath);
  }

  // Helper method to write the pixels to a PPM file.
  private void writePixelsToPPM(PixelInterface[][] compressedImage, String filepath)
      throws IOException {
    StringBuilder finalImage = new StringBuilder();
    finalImage.append("P3").append(System.lineSeparator());
    finalImage.append(this.modelState.getWidth()).append(" ").append(this.modelState.getHeight())
        .append(System.lineSeparator());
    finalImage.append(MAX_VALUE).append(System.lineSeparator());
    for (int row = 0; row < this.modelState.getHeight(); row++) {
      for (int col = 0; col < this.modelState.getWidth(); col++) {
        PixelInterface curPixel = compressedImage[row][col].convertToRGB();
        String curPixelString = curPixel.toString(1);
        finalImage.append(curPixelString).append(" ");
      }
    }
    FileWriter fileWriter;
    fileWriter = new FileWriter(filepath);
    try {
      fileWriter.write(finalImage.toString());
    } catch (IOException e) {
      throw new IOException("Could not write to file");
    }
    fileWriter.close();
  }
}

