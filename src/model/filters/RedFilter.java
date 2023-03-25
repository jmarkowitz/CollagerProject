package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a red filter that can be applied to a layer.
 */
public class RedFilter extends AbstractFilter {

  /**
   * Constructs a red filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public RedFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    return new Pixel(curPixel.getRed(), 0, 0, curPixel.getAlpha());
  }
}
