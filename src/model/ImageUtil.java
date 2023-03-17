package model;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    //System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    //System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    PixelInterface[][] imageGrid = new PixelInterface[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        imageGrid[row][col] = new Pixel(scalePixel(red, maxValue),
            scalePixel(green, maxValue),
            scalePixel(blue, maxValue),
            255);
        //System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
    }
  }

  private static int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }

//  public static PixelInterface[][] getImageGrid() {
//    return this.imageGrid;
//  }
}

