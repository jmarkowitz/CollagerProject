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
    int value = p.getValue();
    return new Pixel(this.produceValidValue(p.getRed() + value),
            this.produceValidValue(p.getGreen() + value),
            this.produceValidValue(p.getBlue() + value),
            this.produceValidValue(p.getAlpha() + value));
  }
}
