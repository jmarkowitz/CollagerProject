package model;

import java.util.Scanner;


/**
 * This class represents an Image. Can read in an image file and return a 2D array of pixels.
 */
public class Image {

  /**
   * Read an image file as a String in the PPM format and return a 2D array of pixels representing
   * the image.
   *
   * @param imageFile     the image file as a string.
   * @param projectHeight the height of the project
   * @param projectWidth  the width of the project
   * @throws IllegalArgumentException if the image file is not in the right format.
   */
  public static PixelInterface[][] readPPM(String imageFile, int projectHeight, int projectWidth)
      throws IllegalArgumentException {
    int width;
    int height;
    PixelInterface[][] imageLayer;

    Scanner sc;
    sc = new Scanner(imageFile);
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Not a PPM file");
    }
    width = sc.nextInt();
    height = sc.nextInt();
    imageLayer = new PixelInterface[projectHeight][projectWidth];
    int maxValue = sc.nextInt();
    for (int row = 0; row < projectHeight; row++) {
      for (int col = 0; col < projectWidth; col++) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if (row < height && col < width) {
          red = sc.nextInt();
          green = sc.nextInt();
          blue = sc.nextInt();
          imageLayer[row][col] = new Pixel(scalePixel(red, maxValue),
              scalePixel(green, maxValue),
              scalePixel(blue, maxValue),
              255);
        } else {
          imageLayer[row][col] = new Pixel(255, 255, 255, 0);
        }
      }
    }
    return imageLayer;
  }

  private static int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }
}

