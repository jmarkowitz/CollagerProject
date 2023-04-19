package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a darken value filter that can be applied to a layer. This filter will darken the
 * value of the current image's pixels.
 */
public class DarkenValueFilter extends AbstractFilter {

  /**
   * Constructs a darken value filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public DarkenValueFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    int value = curPixel.getValue();
    return new Pixel(this.produceValidValue(curPixel.getRed() - value),
        this.produceValidValue(curPixel.getGreen() - value),
        this.produceValidValue(curPixel.getBlue() - value),
        this.produceValidValue(curPixel.getAlpha() - value));
  }
}
