package model;

import static java.lang.Math.max;
import static model.Project.MAX_VALUE;

/**
 * Represents an RGBA pixel that contains three channels of red, green, and blue values and an alpha
 * channel that can range from 0-255. The RGB values represents each value's respective color and
 * the alpha value represents the {@code Pixel}'s transparency, where 0 being fully transparent, and
 * 255 being opaque.
 */
public class Pixel implements PixelInterface {

  private final int redVal;
  private final int greenVal;
  private final int blueVal;
  private final int alphaVal;
  private int value;
  private int intensity;
  private int luma;

  /**
   * Constructs an RGBA pixel.
   *
   * @param red   the pixel's red value
   * @param green the pixel's green value
   * @param blue  the pixel's blue value
   * @param alpha the pixel's alpha value
   * @throws IllegalArgumentException if any of the arguments are less than 0 or greater than 255
   */
  public Pixel(int red, int green, int blue, int alpha) throws IllegalArgumentException {
    this.checkRGB(red, green, blue);
    if (!isValid(alpha)) {
      throw new IllegalArgumentException("Invalid alpha value");
    }
    this.redVal = red;
    this.greenVal = green;
    this.blueVal = blue;
    this.alphaVal = alpha;
    this.initValueIntensityLuma(this.redVal, this.greenVal, this.blueVal);
  }

  /**
   * Constructs and RGB pixel and assigns the alpha channel to be the maximum value of 255.
   *
   * @param red   the pixel's red value
   * @param green the pixel's green value
   * @param blue  the pixel's blue value
   * @throws IllegalArgumentException if any of the arguments are less than 0 or greater than 255
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    this(red, green, blue, MAX_VALUE);
  }

  private void checkRGB(int red, int green, int blue) throws IllegalArgumentException {
    if (!isValid(red)) {
      throw new IllegalArgumentException("Invalid red value");
    } else if (!isValid(green)) {
      throw new IllegalArgumentException("Invalid green value");
    } else if (!isValid(blue)) {
      throw new IllegalArgumentException("Invalid blue value");
    }
  }

  private boolean isValid(int value) {
    return value >= 0 && value <= MAX_VALUE;
  }

  private void initValueIntensityLuma(int red, int green, int blue) {
    int val = max(max(red, blue), green);
    int intensity = (int) Math.round((red + green + blue) / 3.0);
    int luma = (int) Math.round(0.2126 * red + 0.7152 * green + 0.0722 * blue);
    this.value = val;
    this.intensity = intensity;
    this.luma = luma;
  }

  /**
   * Returns the red value of the {@code Pixel}.
   *
   * @return the red value
   */
  @Override
  public int getRed() {
    return this.redVal;
  }

  /**
   * Returns the green value of the {@code Pixel}.
   *
   * @return the green value
   */
  @Override
  public int getGreen() {
    return this.greenVal;
  }

  /**
   * Returns the blue value of the {@code Pixel}.
   *
   * @return the blue value
   */
  @Override
  public int getBlue() {
    return this.blueVal;
  }

  /**
   * Returns the alpha value of the {@code Pixel}.
   *
   * @return the alpha value
   */
  @Override
  public int getAlpha() {
    return this.alphaVal;
  }

  /**
   * Returns the maximum value of the three individual channels (red, green, blue) for a
   * {@code Pixel}.
   *
   * @return the max value of red, green, and blue
   */
  @Override
  public int getValue() {
    return this.value;
  }

  /**
   * Returns the average of the three individual channels (red, green, blue) as an integer value.
   *
   * @return the intensity of the {@code Pixel}.
   */
  @Override
  public int getIntensity() {
    return this.intensity;
  }

  /**
   * Return a double representing the weighted sum of the {@code Pixel}'s values.
   *
   * @return the luma value of the {@code Pixel}.
   */
  @Override
  public int getLuma() {
    return this.luma;
  }

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
  @Override
  public PixelInterface bgPixelConverter(int red, int green, int blue, int alpha) {
    double redD = red;
    double greenD = green;
    double blueD = blue;
    double alphaD = alpha;
    double thisRedD = this.redVal;
    double thisGreenD = this.greenVal;
    double thisBlueD = this.blueVal;
    double thisAlphaD = this.alphaVal;

    double aPrev = alphaD;
    double a0 = (thisAlphaD / MAX_VALUE + alphaD / MAX_VALUE * (1 - thisAlphaD / MAX_VALUE));
    alphaD = a0 * MAX_VALUE;  //calculated a
    redD = (thisAlphaD / MAX_VALUE * thisRedD + redD * (aPrev / MAX_VALUE) * (1
        - thisAlphaD / MAX_VALUE)) * (1 / a0);
    greenD = (thisAlphaD / MAX_VALUE * thisGreenD + greenD * (aPrev / MAX_VALUE) * (1
        - thisAlphaD / MAX_VALUE)) * (1 / a0);
    blueD = (thisAlphaD / MAX_VALUE * thisBlueD + blueD * (aPrev / MAX_VALUE) * (1
        - thisAlphaD / MAX_VALUE)) * (1 / a0);
    int finalRed = (int) Math.round(redD);
    int finalGreen = (int) Math.round(greenD);
    int finalBlue = (int) Math.round(blueD);
    int finalAlpha = (int) Math.round(alphaD);
    return new Pixel(finalRed, finalGreen, finalBlue, finalAlpha);
  }

  /**
   * Converts this {@code Pixel} with the alpha channel and returns a new {@code Pixel} that
   * compresses the alpha value into each RGB value.
   *
   * @return a new {@code Pixel}
   */
  @Override
  public PixelInterface convertToRGB() {
    double alphaFactor = this.alphaVal / (double) MAX_VALUE;
    int redNew = (int) Math.round(this.redVal * alphaFactor);
    int greenNew = (int) Math.round(this.greenVal * alphaFactor);
    int blueNew = (int) Math.round(this.blueVal * alphaFactor);
    return new Pixel(redNew, greenNew, blueNew);
  }

  /**
   * Returns the representation of the pixel as a string. If the argument passed in is 0 it will be
   * in the form of "r g b a" otherwise it will be in the form of "r g b".
   *
   * @param isRGBA boolean flag to determine whether the pixel is an RGBA or not
   * @return the string representing the pixel
   */
  @Override
  public String toString(int isRGBA) {
    if (isRGBA == 0) {
      return String.format("%s %s %s %s", this.getRed(), this.getGreen(), this.getBlue(),
          this.getAlpha());
    } else {
      PixelInterface RGBPixel = this.convertToRGB();
      return String.format("%s %s %s", RGBPixel.getRed(), RGBPixel.getGreen(),
          RGBPixel.getBlue());
    }
  }
}
