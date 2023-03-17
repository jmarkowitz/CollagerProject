package model.filters;

import model.Pixel;
import model.PixelInterface;

public class BrightenLuma extends AbstractFilter {
  public BrightenLuma(int height, int width) {
    super(height, width);}

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Luma = p.getLuma();
    return new Pixel(p.getRed() + Luma,
            p.getGreen() + Luma,
            p.getBlue() + Luma);
  }

  @Override
  public String getFilterName() {
    return "brighten-luma";
  }


  /*
  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];
    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        int Luma = newGrid[row][col].getLuma();
        newGrid[row][col] = new Pixel(grid[row][col].getRed() + Luma,
                grid[row][col].getGreen() + Luma,
                grid[row][col].getBlue() + Luma);
      }
    }
    LayerInterface newLayer = new Layer(layer.getName(), newGrid);
    return newLayer;
  }

   */
}
