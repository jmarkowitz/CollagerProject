package model.filters;
import model.FilterInterface;
import model.Layer;
import model.LayerInterface;
import model.PixelInterface;

public abstract class AbstractFilter implements FilterInterface {
  protected final int height;
  protected final int width;
  protected String filterName;

  public AbstractFilter(int height, int width) {
    this.height = height;
    this.width = width;
  }

  protected abstract PixelInterface getPixelByType(PixelInterface p);

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

  @Override
  public String getFilterName() {
    return this.filterName;
  }

  @Override
  public int produceValidValue(int value) {
    if (value > 255) {
      return 255;
    } else if (value < 0) {
      return 0;
    } else {
      return value;
    }
  }

}
