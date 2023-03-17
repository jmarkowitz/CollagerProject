package model.filters;

import model.Pixel;
import model.PixelInterface;

public class NormalFilter extends AbstractFilter {

  public NormalFilter(int height, int width) {
    super(height, width);
    this.filterName = "normal";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return p;
  }
}
