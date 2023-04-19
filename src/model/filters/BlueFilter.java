package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a blue filter that can be applied to a layer. This filter will keep only the blue
 * channel of the current image's pixels.
 */
public class BlueFilter extends AbstractFilter {

  /**
   * Constructs a blue filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public BlueFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    return new Pixel(0, 0, curPixel.getBlue(), curPixel.getAlpha());
  }
}
