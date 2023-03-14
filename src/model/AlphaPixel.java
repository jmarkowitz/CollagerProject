package model;

public class AlphaPixel extends AbstractPixel implements AlphaPixelInterface{
  final private int alpha;

  public AlphaPixel(int red, int green, int blue, int alpha) {
    super(red, green, blue);
    this.alpha = alpha;
  }

  @Override
  public int getAlpha() {
    return this.alpha;
  }
}
