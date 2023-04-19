package model.filters;

import model.PixelInterface;

/**
 * Represents a normal filter that can be applied to a layer. This will bring the current image's
 * pixels back to normal with no filter applied.
 */
public class NormalFilter extends AbstractFilter {

  /**
   * Constructs a normal filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public NormalFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    return curPixel;
  }
}
