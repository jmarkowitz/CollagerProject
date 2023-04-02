package controller.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModelState;


public class ImageHandler extends AbstractFileHandler<PixelInterface[][]> {

  public ImageHandler(ProjectModelState modelState) {
    super(modelState);
  }

  @Override
  public PixelInterface[][] readFile(String filepath) throws IOException {
    BufferedImage img = ImageIO.read(new File(filepath));
    int width = img.getWidth();
    int height = img.getHeight();
    PixelInterface[][] pixels = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int rgba = img.getRGB(col, row);
        int red = (rgba >> 16) & 0xff;
        int green = (rgba >> 8) & 0xff;
        int blue = rgba & 0xff;
        int alpha = (rgba >> 24) & 0xff;
        pixels[row][col] = new Pixel(red, green, blue, alpha);
      }
    }
    return pixels.clone();
  }

  @Override
  public void writeFile(String filepath) throws IOException {
    String extension = this.getFileExtension(filepath);
    PixelInterface[][] finalImage = this.modelState.compressLayers();
    this.writePixelsToImage(finalImage, filepath, extension);
  }

  private void writePixelsToImage(PixelInterface[][] compressedImage, String filepath,
      String extension)
      throws IOException {
    BufferedImage image = new BufferedImage(this.modelState.getWidth(), this.modelState.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
    for (int row = 0; row < this.modelState.getHeight(); row++) {
      for (int col = 0; col < this.modelState.getWidth(); col++) {
        PixelInterface curPixel = compressedImage[row][col];
        int argb =
            (curPixel.getAlpha() << 24) | (curPixel.getRed() << 16) | (curPixel.getGreen() << 8)
                | curPixel.getBlue();
        image.setRGB(col, row, argb);
      }
    }
    File output = new File(filepath);
    try {
      ImageIO.write(image, extension, output);
    } catch (IOException e) {
      throw new IOException("Unable to write image to file");
    }
  }
}
