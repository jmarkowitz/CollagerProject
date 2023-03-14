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
  double getLuma();

  /**
   * Converts this {@code Pixel} with the alpha channel and returns a new {@code Pixel} that
   * compresses the alpha value into each RGB value.
   *
   * @return a new {@code Pixel}
   */
  PixelInterface convertToRGB();
}
