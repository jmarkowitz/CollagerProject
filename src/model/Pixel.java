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

  private int redVal;
  private int greenVal;
  private int blueVal;
  private int alphaVal;
  private int value;
  private int intensity;
  private double luma;

  /**
   * Constructs an RGBA pixel.
   * @param red the pixel's red value
   * @param green the pixel's green value
   * @param blue the pixel's blue value
   * @param alpha the pixel's alpha value
   * @throws IllegalArgumentException if any of the arguments are less than 0 or greater than 255
   */
  public Pixel(int red, int green, int blue, int alpha) throws IllegalArgumentException {
    this.checkRGB(red, green, blue);
    if (isInvalid(alpha)) {
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
   * @param red the pixel's red value
   * @param green the pixel's green value
   * @param blue the pixel's blue value
   * @throws IllegalArgumentException if any of the arguments are less than 0 or greater than 255
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    this.checkRGB(red, green, blue);
    this.redVal = red;
    this.greenVal = green;
    this.blueVal = blue;
    this.alphaVal = MAX_VALUE;
    this.initValueIntensityLuma(this.redVal, this.greenVal, this.blueVal);
  }

  private void checkRGB(int red, int green, int blue) throws IllegalArgumentException {
    if (isInvalid(red)) {
      throw new IllegalArgumentException("Invalid red value");
    } else if (isInvalid(green)) {
      throw new IllegalArgumentException("Invalid green value");
    } else if (isInvalid(blue)) {
      throw new IllegalArgumentException("Invalid blue value");
    }
  }

  private boolean isInvalid(int value) {
    return value < 0 || value > 255;
  }

  private void initValueIntensityLuma(int red, int green, int blue) {
    int val = max(max(red, blue), green);
    int intensity = (red + green + blue) / 3;
    double luma = 0.2126 * (double) red + 0.7152 * (double) green + 0.0722 * (double) blue;
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
  public double getLuma() {
    return this.luma;
  }

  /**
   * Converts this {@code Pixel} with the alpha channel and returns a new {@code Pixel} that
   * compresses the alpha value into each RGB value.
   *
   * @return a new {@code Pixel}
   */
  @Override
  public PixelInterface convertToRGB() {//TODO normalizeAlpha
    int alphaFactor = this.alphaVal / MAX_VALUE;
    int redNew = this.redVal * alphaFactor;
    int greenNew = this.greenVal * alphaFactor;
    int blueNew = this.blueVal * alphaFactor;
    //TODO: make setters
    return new Pixel(redNew, greenNew, blueNew);
  }



}
