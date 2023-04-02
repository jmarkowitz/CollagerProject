package model.filters;

import static model.Project.MAX_VALUE;

import model.FilterInterface;
import model.PixelInterface;

/**
 * Represents an abstract filter that can be used on a layer. Allows the user to apply a filter, and
 * get the name of the filter.
 */
public abstract class AbstractFilter implements FilterInterface {

  protected final int height;

  protected final int width;

  /**
   * Constructs an AbstractFilter.
   *
   * @param height the height of the layer
   * @param width  the width of the layer
   */
  public AbstractFilter(int height, int width) {
    this.height = height;
    this.width = width;
  }

  protected abstract PixelInterface getPixelByType(PixelInterface curPixel, PixelInterface bgPixel);

  /**
   * Applies the filter to the given layer and returns the layer with the filtered pixels.
   *
   * @param curImage the layer to be filtered
   * @param bgImage the background image used for certain filters
   * @return the filtered layer
   */
  @Override
  public PixelInterface[][] apply(PixelInterface[][] curImage, PixelInterface[][] bgImage) {
    PixelInterface[][] newGrid = new PixelInterface[height][width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        newGrid[row][col] = getPixelByType(curImage[row][col], bgImage[row][col]);
      }
    }
    return newGrid;
  }

  /**
   * Ensures that values cannot exceed 255 or go below 0. Useful for brightening and darkening
   * filters. If the value is greater than 255, it is set to 255. If the value is less than 0, it is
   * set to 0.
   *
   * @param value the value to be checked
   * @return 0 if it is below 0, 255 if it is above 255, or the original value if it is 0-255
   */
  protected int produceValidValue(int value) {
    if (value > 255) {
      return 255;
    } else
      return Math.max(value, 0);
  }

  /**
   * Changes the range of the value from 0-255 to 0-1.
   * @param value the value to be changed
   * @return the value as a double in the range of 0-1
   */
  protected double changeRange(int value) {
    return value / (double) MAX_VALUE;
  }

}
