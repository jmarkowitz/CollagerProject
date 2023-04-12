package model.filters;

import model.Pixel;
import model.PixelInterface;

/**
 * Represents a difference filter that takes two pixels RGB components and subtracts them
 * component-wise. The resulting pixel is the absolute value of the difference.
 */
public class DifferenceFilter extends AbstractFilter {


  /**
   * Constructs an AbstractFilter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public DifferenceFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    int rNew = Math.abs(curPixel.getRed() - bgPixel.getRed());
    int gNew = Math.abs(curPixel.getGreen() - bgPixel.getGreen());
    int bNew = Math.abs(curPixel.getBlue() - bgPixel.getBlue());
    return new Pixel(rNew, gNew, bNew, curPixel.getAlpha());
  }
}
