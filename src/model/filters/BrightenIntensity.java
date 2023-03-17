package model.filters;

import model.Pixel;
import model.PixelInterface;

public class BrightenIntensity extends AbstractFilter {


  public BrightenIntensity(int height, int width) {
    super(height, width);
    this.filterName = "brighten-intensity";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Intensity = p.getIntensity();
    return new Pixel(p.getRed() + Intensity,
        p.getGreen() + Intensity,
        p.getBlue() + Intensity,
        p.getAlpha());
  }
}
