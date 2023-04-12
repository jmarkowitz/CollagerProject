package model.filters;

import model.HSLUtil;
import model.Pixel;
import model.PixelInterface;

/**
 * Represents a screen blend filter that will use the lightness value of the composite image's
 * pixels below and the current layer's pixels and return the resulting pixel.
 */
public class ScreenFilter extends AbstractFilter {

  /**
   * Constructs a brightening blend filter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public ScreenFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel) {
    // current pixel values converted to HSL
    double curRed = this.changeRange(curPixel.getRed());
    double curGreen = this.changeRange(curPixel.getGreen());
    double curBlue = this.changeRange(curPixel.getBlue());
    Double[] valuesCurHSL = HSLUtil.convertRGBToHSL(curRed, curGreen, curBlue);
    double curHue = valuesCurHSL[0];
    double curSat = valuesCurHSL[1];
    double curLight = valuesCurHSL[2];

    // bg pixel values converted to HSL
    double bgRed = this.changeRange(bgPixel.getRed());
    double bgGreen = this.changeRange(bgPixel.getGreen());
    double bgBlue = this.changeRange(bgPixel.getBlue());
    Double[] valuesBGHSL = HSLUtil.convertRGBToHSL(bgRed, bgGreen, bgBlue);
    double bgLight = valuesBGHSL[2];

    // HSL difference filter math
    double convertedLight = 1 - ((1 - curLight) * (1 - bgLight));

    // convert back to RGB
    Integer[] valuesRGB = HSLUtil.convertHSLToRGB(curHue, curSat, convertedLight);

    return new Pixel(valuesRGB[0], valuesRGB[1], valuesRGB[2], curPixel.getAlpha());
  }
}
