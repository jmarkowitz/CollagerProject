package controller.commands;

import controller.CollagerCommand;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import model.FilterInterface;
import model.LayerInterface;
import model.PixelInterface;
import model.ProjectModel;


public class SaveProject implements CollagerCommand {

  private final Scanner scanner;

  public SaveProject(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Saves and exports all the data in the model to a file location that includes the following
   * below.
   * <ul>
   * <li> the project name</li>
   * <li> the width and height</li>
   * <li> the maximum value for one pixel</li>
   * <li> all layers' name, their respective filter and the UNMODIFIED RGBA pixels in each layer
   * separated by a new line </li>
   * </ul>
   *
   * @throws IllegalStateException if this method is called before a project has been created or
   *                               loaded
   */
  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      String filepath = this.scanner.next();
      int projectWidth = model.getWidth();
      int projectHeight = model.getHeight();
      Map<String, LayerInterface> projectLayers = model.getLayers();
      FileWriter fileWriter = null;
      try {
        fileWriter = new FileWriter(filepath);
        fileWriter.write(this.formatExport(projectWidth, projectHeight, projectLayers));
        fileWriter.close();
      } catch (IOException ex) {
        System.err.println(ex.getMessage());
      }//TODO: make filewriter class
    }
  }

  private String formatExport(int width, int height, Map<String, LayerInterface> projectLayers) {
    StringBuilder finalString = new StringBuilder();
    finalString.append("C1").append(System.lineSeparator());
    finalString.append(width).append(" ").append(height).append(System.lineSeparator());
    for (Map.Entry<String, LayerInterface> mapLayer : projectLayers.entrySet()) {
      LayerInterface currentLayer = mapLayer.getValue();
      finalString.append(currentLayer.getName()).append(" ").append(currentLayer.getFilter());
      PixelInterface[][] curLayerPixels = currentLayer.getPixelGrid();
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
            finalString.append(curLayerPixels[row][col].toString(0)).append(System.lineSeparator());
        }
      }
    }
    return finalString.toString();
  }
}
