package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a green filter that can be applied to a layer. This filter will keep only the green
 * channel of the current image's pixels.
 */
public class GreenFilter extends AbstractFilter {

  /**
   * Constructs a green filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public GreenFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    return new Pixel(0, curPixel.getGreen(), 0, curPixel.getAlpha());
  }
}
