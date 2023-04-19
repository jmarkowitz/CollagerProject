package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a brighten value filter that can be applied to a layer. This filter will brighten the
 * value of the current image's pixels.
 */
public class BrightenValueFilter extends AbstractFilter {

  /**
   * Constructs a brighten value filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public BrightenValueFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    int value = curPixel.getValue();
    return new Pixel(this.produceValidValue(curPixel.getRed() + value),
        this.produceValidValue(curPixel.getGreen() + value),
        this.produceValidValue(curPixel.getBlue() + value),
        this.produceValidValue(curPixel.getAlpha() + value));
  }
}
