package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a brighten luma filter that can be applied to a layer.
 */
public class BrightenLumaFilter extends AbstractFilter {

  /**
   * Constructs a brighten luma filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public BrightenLumaFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    int luma = curPixel.getLuma();
    return new Pixel(this.produceValidValue(curPixel.getRed() + luma),
        this.produceValidValue(curPixel.getGreen() + luma),
        this.produceValidValue(curPixel.getBlue() + luma),
        this.produceValidValue(curPixel.getAlpha() + luma));
  }
}
