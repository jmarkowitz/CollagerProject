package model;

public abstract class AbstractPixel implements PixelInterface{
  private final int redVal;
  private final int greenVal;
  private final int blueVal;

  public AbstractPixel(int red, int green, int blue){
    this.redVal = red;
    this.greenVal = green;
    this.blueVal = blue;
  }

  @Override
  public int getRed() {
    return this.redVal;
  }

  @Override
  public int getGreen() {
    return this.greenVal;
  }

  @Override
  public int getBlue() {
    return this.blueVal;
  }

}
