package controller.commands;

import controller.CollagerCommand;
import java.util.Map;
import model.LayerInterface;
import model.PixelInterface;
import model.ProjectModel;

public class SaveImage implements CollagerCommand {

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) {
    int index = 0;
    Map<String, LayerInterface> allLayers = model.getLayers();
    for (Map.Entry<String, LayerInterface> layer : allLayers.entrySet()) {
      LayerInterface currentLayer = layer.getValue();
      if (!currentLayer.getFilter().getFilterName().equals("Normal")) {
        LayerInterface filteredLayer = currentLayer.getFilter().apply(currentLayer);
        currentLayer = filteredLayer;
      }
      PixelInterface[][] currentPixelGrid = currentLayer.getPixelGrid();
      int a1 = 0;
      int r1 = 0;
      int g1 = 0;
      int b1 = 0;
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getHeight(); col++) {
          int r = currentPixelGrid[row][col].getRed();
          int g = currentPixelGrid[row][col].getGreen();
          int b = currentPixelGrid[row][col].getBlue();
          if (index == 0) {
            int a = currentPixelGrid[row][col].getAlpha();
          }
          if (index != 0) {
            int aPrev = a;
            int a = currentPixelGrid[row][col].getAlpha();
            a1 = a / 255 + a1 / 255 * (1 - a / 255);
            r1 = r;
            g1 = g;
            b1 = b;
          }
        }
      }
      index++;


    }

  }
}
