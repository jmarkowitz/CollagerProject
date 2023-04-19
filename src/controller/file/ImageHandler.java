package controller.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModelState;

/**
 * Represents a handler for image files. Allows the user to read and write images files such as
 * .png, .jpeg or .jpg.
 */
public class ImageHandler extends AbstractFileHandler<PixelInterface[][]> {

  /**
   * Constructs an ImageHandler object.
   *
   * @param modelState the model state for the program
   */
  public ImageHandler(ProjectModelState modelState) {
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

  /**
   * This method read the information to a file.
   *
   * @param filepath the filepath of a file.
   * @throws IOException              if the file is invalid.
   * @throws IllegalArgumentException if the file extension is invalid.
   */
  @Override
  public void writeFile(String filepath) throws IOException, IllegalArgumentException {
    String extension = this.getFileExtension(filepath);
    PixelInterface[][] finalImage = this.modelState.compressLayers();
    BufferedImage image;
    switch (extension) {
      case "png":
        image = this.pixelsToImage(finalImage, BufferedImage.TYPE_INT_ARGB);
        break;
      case "jpeg":
      case "jpg":
        image = this.pixelsToImage(finalImage, BufferedImage.TYPE_INT_RGB);
        break;
      default:
        throw new IllegalArgumentException("Invalid file extension");
    }
    File output = new File(filepath);
    try {
      ImageIO.write(image, extension, output);
    } catch (IOException e) {
      throw new IOException("Unable to write image to file");
    }
  }

  // Helper method to write pixels to an image and will add transparency and changed the buffered
  // image type depending on what the extension is
  private BufferedImage pixelsToImage(PixelInterface[][] compressedImage, int type) {
    BufferedImage image = new BufferedImage(this.modelState.getWidth(), this.modelState.getHeight(),
        type);
    for (int row = 0; row < this.modelState.getHeight(); row++) {
      for (int col = 0; col < this.modelState.getWidth(); col++) {
        PixelInterface curPixel = compressedImage[row][col];
        if (type == BufferedImage.TYPE_INT_ARGB) {
          int pixelBit =
              (curPixel.getAlpha() << 24)
                  | (curPixel.getRed() << 16)
                  | (curPixel.getGreen() << 8)
                  | curPixel.getBlue();
          image.setRGB(col, row, pixelBit);
        } else {
          curPixel = curPixel.convertToRGB();
          int pixelBit = (curPixel.getRed() << 16) |
              (curPixel.getGreen() << 8) |
              curPixel.getBlue();
          image.setRGB(col, row, pixelBit);
        }
      }
    }
    return image;
  }
}
