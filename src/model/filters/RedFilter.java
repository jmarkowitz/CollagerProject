package model.filters;

import model.Pixel;
import model.PixelInterface;

public class RedFilter extends AbstractFilter {

  public RedFilter(int height, int width) {
    super(height, width);
    this.filterName = "red-component";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(p.getRed(), 0, 0, p.getAlpha());
  }
}
