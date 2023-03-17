package model.filters;

import model.Pixel;
import model.PixelInterface;

public class DarkenIntensity extends AbstractFilter {


  public DarkenIntensity(int height, int width) {
    super(height, width);
    this.filterName = "darken-intensity";
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Intensity = p.getIntensity();
    return new Pixel(p.getRed() - Intensity,
            p.getGreen() - Intensity,
            p.getBlue() - Intensity,
        p.getAlpha());
  }
}
