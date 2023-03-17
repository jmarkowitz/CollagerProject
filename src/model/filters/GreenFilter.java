package model.filters;

import model.Pixel;
import model.PixelInterface;

public class GreenFilter extends AbstractFilter {
  public GreenFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(0, p.getGreen(), 0);
  }

  @Override
  public String getFilterName() {
    return "green-component";
  }


  /*
  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];
    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        newGrid[row][col] = new Pixel(0, grid[row][col].getGreen(), 0);
      }
    }
    LayerInterface newLayer = new Layer(layer.getName(), newGrid);
    return newLayer;
  }

   */
}
