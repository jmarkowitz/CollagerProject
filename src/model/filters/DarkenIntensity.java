package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a darken intensity filter that can be applied to a layer.
 */
public class DarkenIntensity extends AbstractFilter {

  /**
   * Constructs a darken intensity filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public DarkenIntensity(int height, int width) {
    super(height, width);
    this.filterName = "darken-intensity";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int intensity = p.getIntensity();
    return new Pixel(this.produceValidValue(p.getRed() - intensity),
        this.produceValidValue(p.getGreen() - intensity),
        this.produceValidValue(p.getBlue() - intensity),
        this.produceValidValue(p.getAlpha() - intensity));
  }
}
