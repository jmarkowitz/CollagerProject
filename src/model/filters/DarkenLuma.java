package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a darken luma filter that can be applied to a layer.
 */
public class DarkenLuma extends AbstractFilter {

  /**
   * Constructs a darken luma filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public DarkenLuma(int height, int width) {
    super(height, width);
    this.filterName = "darken-luma";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int luma = p.getLuma();
    return new Pixel(this.produceValidValue(p.getRed() - luma),
        this.produceValidValue(p.getGreen() - luma),
        this.produceValidValue(p.getBlue() - luma),
        this.produceValidValue(p.getAlpha() - luma));
  }
}
