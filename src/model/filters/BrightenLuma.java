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
    int luma = p.getLuma();
    return new Pixel(this.produceValidValue(p.getRed() + luma),
            this.produceValidValue(p.getGreen() + luma),
            this.produceValidValue(p.getBlue() + luma),
            this.produceValidValue(p.getAlpha() + luma));
  }
}
