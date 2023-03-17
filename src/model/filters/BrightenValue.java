package model.filters;

import model.Pixel;
import model.PixelInterface;

public class BrightenValue extends AbstractFilter {
  public BrightenValue(int height, int width) {
    super(height, width);}

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Value = p.getValue();
    return new Pixel(p.getRed() + Value,
            p.getGreen() + Value,
            p.getBlue() + Value);
  }

  @Override
  public String getFilterName() {
    return "brighten-value";
  }


  /*
  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];
    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        int Value = newGrid[row][col].getValue();
        newGrid[row][col] = new Pixel(grid[row][col].getRed() + Value,
                grid[row][col].getGreen() + Value,
                grid[row][col].getBlue() + Value);
      }
    }
    LayerInterface newLayer = new Layer(layer.getName(), newGrid);
    return newLayer;
  }

   */
}
