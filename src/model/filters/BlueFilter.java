package model.filters;

import model.FilterInterface;
import model.Layer;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;

public class BlueFilter extends AbstractFilter {


  public BlueFilter(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    return new Pixel(0, 0,p.getBlue());
  }


  /*
  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];
    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        newGrid[row][col] = new Pixel(0, 0, grid[row][col].getBlue());
      }
    }
    LayerInterface newLayer = new Layer(layer.getName(), newGrid);
    return newLayer;
  }

   */
}
