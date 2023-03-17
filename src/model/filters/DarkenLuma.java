package model.filters;

import model.Pixel;
import model.PixelInterface;

public class DarkenLuma extends AbstractFilter {
  public DarkenLuma(int height, int width) {
    super(height, width);
    this.filterName = "darken-luma";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Luma = p.getLuma();
    return new Pixel(p.getRed() - Luma,
            p.getGreen() - Luma,
            p.getBlue() - Luma,
        p.getAlpha());
  }
}
