package model;

import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class Image {

  /**
   * Read an image file as a String in the PPM format and return a 2D array of pixels representing
   * the image.
   *
   * @param imageFile the image file as a string.
   */
  public static PixelInterface[][] readPPM(String imageFile) throws IllegalArgumentException {
    int width;
    int height;
    PixelInterface[][] imageLayer;

    Scanner sc;
    sc = new Scanner(imageFile);
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
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
    return imageLayer;
  }

//  public void readProjectFile(String filename) throws IllegalArgumentException{
//    String fileAsString = readFileAsString(filename);
//    Scanner sc;
//    sc = new Scanner(fileAsString);
//    String token;
//    token = sc.next();
//    if (!token.equals("C1")) {
//      System.out.println("Invalid Project file: plain RAW file should begin with C1");
//    }
//    width = sc.nextInt();
//    height = sc.nextInt();
//    int maxValue = sc.nextInt();
//
//    for (int row = 0; row < height; row++) {
//      for (int col = 0; col < width; col++) {
//        int red = sc.nextInt();
//        int green = sc.nextInt();
//        int blue = sc.nextInt();
//        imageLayer[row][col] = new Pixel(scalePixel(red, maxValue),
//            scalePixel(green, maxValue),
//            scalePixel(blue, maxValue),
//            255);
//      }
//    }
//  }

//  public int getImageWidth() throws IllegalStateException {
//    if (!this.isValidFile) {
//      throw new IllegalStateException("Must read the file first");
//    }
//    return this.width;
//  }
//
//  public int getImageHeight() throws IllegalStateException {
//    if (!this.isValidFile) {
//      throw new IllegalStateException("Must read the file first");
//    }
//    return this.height;
//  }
//
//  public PixelInterface[][] getImageLayer() throws IllegalStateException {
//    if (!this.isValidFile) {
//      throw new IllegalStateException("Must read the file first");
//    }
//    return this.imageLayer;
//  }

  private static int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }
}

