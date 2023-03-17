package model.filters;

import model.Pixel;
import model.PixelInterface;

public class BrightenLuma extends AbstractFilter {
  public BrightenLuma(int height, int width) {
    super(height, width);
    this.filterName = "brighten-luma";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Luma = p.getLuma();
    return new Pixel(p.getRed() + Luma,
            p.getGreen() + Luma,
            p.getBlue() + Luma,
        p.getAlpha());
  }
}
