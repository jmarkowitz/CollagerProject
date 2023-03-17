package model.filters;

import model.Pixel;
import model.PixelInterface;

public class RedFilter extends AbstractFilter {

  public RedFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(p.getRed(), 0, 0);
  }

  @Override
  public String getFilterName() {
    return "red-component";
  }


  /*
  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];
    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        newGrid[row][col] = new Pixel(grid[row][col].getRed(), 0, 0);
      }
    }
    LayerInterface newLayer = new Layer(layer.getName(), newGrid);
    return newLayer;
  }

   */
}
