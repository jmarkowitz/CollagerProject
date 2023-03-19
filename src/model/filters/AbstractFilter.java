package model.filters;

import model.FilterInterface;
import model.Layer;
import model.LayerInterface;
import model.PixelInterface;

/**
 * Represents an abstract filter that can be used on a layer. Allows the user to apply a filter, and
 * get the name of the filter.
 */
public abstract class AbstractFilter implements FilterInterface {

  protected final int height;

  protected final int width;
  protected String filterName;

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

  protected abstract PixelInterface getPixelByType(PixelInterface p);

  /**
   * Applies the filter to the given layer and returns the layer with the filtered pixels.
   *
   * @param layer the layer to be filtered
   * @return the filtered layer
   */
  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        newGrid[row][col] = getPixelByType(grid[row][col]);
      }
    }
    return new Layer(layer.getName(), newGrid);
  }

  /**
   * Returns the name of the filter.
   *
   * @return the new name of the filter
   */
  @Override
  public String getFilterName() {
    return this.filterName;
  }

  /**
   * Ensures that values cannot exceed 255 or go below 0. Useful for brightening and darkening
   * filters. If the value is greater than 255, it is set to 255. If the value is less than 0, it is
   * set to 0.
   *
   * @param value the value to be checked
   * @return 0 if it is below 0, 255 if it is above 255, or the original value if it is between 0
   * and 255
   */
  protected int produceValidValue(int value) {
    if (value > 255) {
      return 255;
    } else if (value < 0) {
      return 0;
    } else {
      return value;
    }
  }
}
