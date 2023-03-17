package model.filters;

import model.Pixel;
import model.PixelInterface;

public class DarkenValue extends AbstractFilter {
  public DarkenValue(int height, int width) {
    super(height, width);
    this.filterName = "darken-value";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Value = p.getValue();
    return new Pixel(p.getRed() - Value,
            p.getGreen() - Value,
            p.getBlue() - Value,
        p.getAlpha());
  }
}
