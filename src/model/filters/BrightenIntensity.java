package model.filters;

import model.FilterInterface;
import model.Layer;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;

public class BrightenIntensity extends AbstractFilter {

  public BrightenIntensity(int height, int width) {
    super(height, width);
  }

  @Override
  protected PixelInterface getPixelByType(PixelInterface p) {
    int Intensity = p.getIntensity();
    return new Pixel(p.getRed() + Intensity,
            p.getGreen() + Intensity,
            p.getBlue() + Intensity);
  }

  /*
  @Override
  public LayerInterface apply(LayerInterface layer) {
    PixelInterface[][] grid = layer.getPixelGrid();
    PixelInterface[][] newGrid = new PixelInterface[height][width];
    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        int Intensity = newGrid[row][col].getIntensity();
        newGrid[row][col] = new Pixel(grid[row][col].getRed() + Intensity,
                grid[row][col].getGreen() + Intensity,
                grid[row][col].getBlue() + Intensity);
      }
    }
    LayerInterface newLayer = new Layer(layer.getName(), newGrid);
    return newLayer;
  }

   */
}
