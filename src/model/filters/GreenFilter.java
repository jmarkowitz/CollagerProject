package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a green filter that can be applied to a layer.
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
    this.filterName = "green-component";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(0, p.getGreen(), 0, p.getAlpha());
  }
}
