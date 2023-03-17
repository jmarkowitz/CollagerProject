package model.filters;

import model.Pixel;
import model.PixelInterface;

public class BrightenValue extends AbstractFilter {
  public BrightenValue(int height, int width) {
    super(height, width);
    this.filterName = "brighten-value";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Value = p.getValue();
    return new Pixel(p.getRed() + Value,
            p.getGreen() + Value,
            p.getBlue() + Value,
        p.getAlpha());
  }
}
