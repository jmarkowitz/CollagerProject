package controller.commands;

import static model.Project.MAX_VALUE;

import controller.CollagerCommand;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import model.FilterInterface;
import model.LayerInterface;
import model.PixelInterface;
import model.ProjectModel;
import view.ProjectView;

public class SaveImage implements CollagerCommand {

  private final Scanner scanner;
  private final ProjectView view;

  public SaveImage(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) throws IOException {
    while (this.scanner.hasNext()) {
      // initial ppm file setup
      String imagePath = this.scanner.next();
      StringBuilder finalImage = new StringBuilder();
      int height = 0;
      int width = 0;
      try {
        height = model.getHeight();
        width = model.getWidth();
      } catch (IllegalStateException e) {
        view.renderMessage(e.getMessage());
        throw new IOException();
      }
      finalImage.append("P3").append(System.lineSeparator());
      finalImage.append(width).append(" ").append(height)
          .append(System.lineSeparator());
      finalImage.append(MAX_VALUE).append(System.lineSeparator());

      // main loop for looping through all layers
      int index = 0;
      Map<String, LayerInterface> allLayers = null;
      Map<String, FilterInterface> allFilters = null;
      try {
        allLayers = model.getLayers();
        allFilters = model.getAllFilters();
      } catch (IllegalStateException e) {
        view.renderMessage(e.getMessage());
        throw new IOException();
      }
      PixelInterface[][] finalPixelGrid = null;
      for (Map.Entry<String, LayerInterface> layer : allLayers.entrySet()) {
        LayerInterface currentLayer = layer.getValue();
        FilterInterface currentFilter = allFilters.get(currentLayer.getFilter());
        LayerInterface filteredLayer = currentFilter.apply(currentLayer);
        if (index == 0) {
          finalPixelGrid = filteredLayer.getPixelGrid();
        }
        finalPixelGrid = filteredLayer.getPixelGrid();//TODO: fix this
        PixelInterface[][] curGrid = filteredLayer.getPixelGrid();
        this.compressLayer(height, width, curGrid, finalPixelGrid);
        index++;
      }
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          finalImage.append(finalPixelGrid[row][col].toString(1)).append(" ");
        }
      }
      this.imageWrite(imagePath, finalImage);
      this.view.renderMessage("Image was successfully saved at: " + imagePath + System.lineSeparator());
      break;
    }
  }

  private PixelInterface[][] compressLayer(int height, int width, PixelInterface[][] currentGrid,
      PixelInterface[][] prevGrid) {
    PixelInterface[][] updatedGrid = new PixelInterface[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        PixelInterface prevPixel = prevGrid[row][col];
        PixelInterface currentPixel = currentGrid[row][col];
        PixelInterface newPixel = currentPixel.bgPixelConverter(prevPixel.getRed(),
            prevPixel.getGreen(), prevPixel.getBlue(), prevPixel.getAlpha());
        updatedGrid[row][col] = newPixel;
      }
    }
    return updatedGrid;
  }

  private void imageWrite(String imagePath, StringBuilder finalImage) throws IOException {
    String workingDir = System.getProperty("user.dir");
    String absoluteFilePath = workingDir + File.separator + imagePath;
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(absoluteFilePath);
      fileWriter.write(finalImage.toString());
      fileWriter.close();
    } catch (IOException ex) {
      view.renderMessage(ex.getMessage() + System.lineSeparator());
      throw new IOException();
    }
  }
}
