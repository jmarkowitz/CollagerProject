package model;

/**
 * Represents any functionality and observations to be made specific to a {@code Pixel}. Allows
 * certain fields to be accessed and allows for conversion from RGBA to RGB.
 */
public interface PixelInterface {

  /**
   * Returns the red value of the {@code Pixel}.
   *
   * @return the red value
   */
  int getRed();

  /**
   * Returns the green value of the {@code Pixel}.
   *
   * @return the green value
   */
  int getGreen();

  /**
   * Returns the blue value of the {@code Pixel}.
   *
   * @return the blue value
   */
  int getBlue();

  /**
   * Returns the alpha value of the {@code Pixel}.
   *
   * @return the alpha value
   */
  int getAlpha();

  /**
   * Returns the maximum value of the three individual channels (red, green, blue) for a
   * {@code Pixel}.
   *
   * @return the max value of red, green, and blue
   */
  int getValue();

  /**
   * Returns the average of the three individual channels (red, green, blue) as an integer value.
   *
   * @return the intensity of the {@code Pixel}.
   */
  int getIntensity();

  /**
   * Return a double representing the weighted sum of the {@code Pixel}'s values.
   *
   * @return the luma value of the {@code Pixel}.
   */
  int getLuma();

  /**
   * Uses the given red, green, blue, and alpha values to use alpha composition with this
   * {@code Pixel}'s values and creates a new compressed {@code Pixel} and returns it.
   *
   * @param red   the red value of the bg pixel
   * @param green the green value of the bg pixel
   * @param blue  the blue value of the bg pixel
   * @param alpha the alpha value of the bg pixel
   * @return the new compressed {@code Pixel}
   */
  PixelInterface bgPixelConverter(int red, int green, int blue, int alpha);


  /**
   * Converts this {@code Pixel} with the alpha channel and returns a new {@code Pixel} that
   * compresses the alpha value into each RGB value.
   *
   * @return a new {@code Pixel}
   */
  PixelInterface convertToRGB();

  /**
   * Returns the representation of the pixel as a string. If the argument passed in is 0 it will be
   * in the form of "r g b a" otherwise it will be in the form of "r g b".
   *
   * @param isRGBA boolean flag to determine whether the pixel is an RGBA or not
   * @return the string representing the pixel
   */
  String toString(int isRGBA);
}
