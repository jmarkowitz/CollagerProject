package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a brighten intensity filter that can be applied to a layer. This filter will brighten
 * the intensity of the current image's pixels.
 */
public class BrightenIntensityFilter extends AbstractFilter {

  /**
   * Constructs a brighten intensity filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public BrightenIntensityFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    int intensity = curPixel.getIntensity();
    return new Pixel(this.produceValidValue(curPixel.getRed() + intensity),
        this.produceValidValue(curPixel.getGreen() + intensity),
        this.produceValidValue(curPixel.getBlue() + intensity),
        this.produceValidValue(curPixel.getAlpha() + intensity));
  }
}
