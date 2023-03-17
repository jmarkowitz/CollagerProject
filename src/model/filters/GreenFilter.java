package model.filters;

import model.Pixel;
import model.PixelInterface;

public class GreenFilter extends AbstractFilter {
  public GreenFilter(int height, int width) {
    super(height, width);
    this.filterName = "green-component";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(0, p.getGreen(), 0, p.getAlpha());
  }
}
