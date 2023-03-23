package controller.commands;

import controller.CollagerCommand;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import model.LayerInterface;
import model.PixelInterface;
import model.ProjectModel;
import view.ProjectView;

/**
 * Saves and exports all the data in the model to a file location.
 */
public class SaveProject implements CollagerCommand {

  private final String filepath;

  /**
   * Constructs a SaveProject object.
   *
   * @param scanner the scanner to read the file path
   * @param view    the view to render the message
   */
  public SaveProject(String filepath) {
    this.filepath = filepath;
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
  public void execute(ProjectModel model, ProjectView view) throws IOException {
      int projectWidth;
      int projectHeight;
      Map<String, LayerInterface> projectLayers;
      try {
        projectWidth = model.getWidth();
        projectHeight = model.getHeight();
        projectLayers = model.getLayers();
      } catch (IllegalStateException | IllegalArgumentException e) {
        view.renderMessage(e.getMessage());
        throw new IOException();
      }
      FileWriter fileWriter = null;
      try {
        fileWriter = new FileWriter(filepath);
        fileWriter.write(this.formatExport(projectWidth, projectHeight, projectLayers));
        view.renderMessage(
            "Project was successfully saved at: " + filepath + System.lineSeparator());
        fileWriter.close();
      } catch (IOException ex) {
        view.renderMessage(ex.getMessage() + System.lineSeparator());
        throw new IOException();
      }
  }

  private String formatExport(int width, int height, Map<String, LayerInterface> projectLayers) {
    StringBuilder finalString = new StringBuilder();
    finalString.append("C1").append(System.lineSeparator());
    finalString.append(width).append(" ").append(height).append(System.lineSeparator());
    finalString.append("255").append(System.lineSeparator());
    for (Map.Entry<String, LayerInterface> mapLayer : projectLayers.entrySet()) {
      LayerInterface currentLayer = mapLayer.getValue();
      String currentLayerName = mapLayer.getKey();
      finalString.append(currentLayerName).append(" ").append(currentLayer.getFilter())
          .append(System.lineSeparator());
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
