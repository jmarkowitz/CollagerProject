package model.filters;
import model.FilterInterface;
import model.Layer;
import model.LayerInterface;
import model.PixelInterface;

public abstract class AbstractFilter implements FilterInterface {
  private final int height;
  private final int width;

  public AbstractFilter(int height, int width) {
    this.height = height;
    this.width = width;
  }

  protected abstract PixelInterface getPixelByType(PixelInterface p);

  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];

    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        newGrid[row][col] = getPixelByType(grid[row][col]);
      }
    }
    return new Layer(layer.getName(), newGrid);
  }
}
