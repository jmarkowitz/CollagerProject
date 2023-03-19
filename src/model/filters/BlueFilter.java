package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a blue filter that can be applied to a layer.
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
    this.filterName = "blue-component";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(0, 0, p.getBlue(), p.getAlpha());
  }
}
