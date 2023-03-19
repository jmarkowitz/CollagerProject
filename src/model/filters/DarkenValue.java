package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a darken value filter that can be applied to a layer.
 */
public class DarkenValue extends AbstractFilter {

  /**
   * Constructs a darken value filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public DarkenValue(int height, int width) {
    super(height, width);
    this.filterName = "darken-value";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int value = p.getValue();
    return new Pixel(this.produceValidValue(p.getRed() - value),
        this.produceValidValue(p.getGreen() - value),
        this.produceValidValue(p.getBlue() - value),
        this.produceValidValue(p.getAlpha() - value));
  }
}
