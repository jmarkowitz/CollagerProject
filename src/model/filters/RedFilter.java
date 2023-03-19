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
    this.filterName = "red-component";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(p.getRed(), 0, 0, p.getAlpha());
  }
}
