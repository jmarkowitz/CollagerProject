package model.filters;

import model.Pixel;
import model.PixelInterface;

public class BlueFilter extends AbstractFilter {

  public BlueFilter(int height, int width) {
    super(height, width);
    this.filterName = "blue-component";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(0, 0, p.getBlue(), p.getAlpha());
  }
}
