package controller.file;

import java.io.IOException;
import java.util.Map;
import model.FilterInterface;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModelState;

public abstract class AbstractFileHandler<F> implements FileHandler<F> {

   protected final int projectHeight;
   protected final int projectWidth;

  protected final Map<String, LayerInterface> layerMap;
  protected final Map<String, FilterInterface> filterMap;

  public AbstractFileHandler(ProjectModelState modelState) {
    this.projectHeight = modelState.getHeight();
    this.projectWidth = modelState.getWidth();
    this.layerMap = modelState.getLayers();
    this.filterMap = modelState.getAllFilters();
  }

  protected String getFileExtension(String filepath) {
    int dotIndex = filepath.lastIndexOf('.');
    if (dotIndex == -1 || dotIndex == filepath.length() - 1) {
      return "";
    } else {
      return filepath.substring(dotIndex + 1).toLowerCase();
    }
  }

  protected int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }

  protected PixelInterface[][] resizePixelArray(PixelInterface[][] inputArray, int projectHeight,
      int projectWidth) {
    if (projectHeight == inputArray.length && projectWidth == inputArray[0].length) {
      return inputArray;
    }
    PixelInterface[][] outputArray = new PixelInterface[projectHeight][projectWidth];
    for (int row = 0; row < projectHeight; row++) {
      for (int col = 0; col < projectWidth; col++) {
        if (col <= inputArray[0].length - 1 && row <= inputArray.length - 1) {
          outputArray[row][col] = inputArray[row][col];
        } else {
          outputArray[row][col] = new Pixel(255, 255, 255, 0);
        }
      }
    }
    return outputArray;
  }

}
