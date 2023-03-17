package controller.commands;

import static model.Project.MAX_VALUE;

import controller.CollagerCommand;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import model.FilterInterface;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;
import model.Project;
import model.ProjectModel;
import model.ProjectModelState;

public class SaveImage implements CollagerCommand {

  private final Scanner scanner;

  public SaveImage(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      // initial ppm file setup
      String imagePath = this.scanner.next();
      StringBuilder finalImage = new StringBuilder();
      int height = model.getHeight();
      int width = model.getWidth();
      finalImage.append("P3").append(System.lineSeparator());
      finalImage.append(width).append(" ").append(height)
          .append(System.lineSeparator());
      finalImage.append(MAX_VALUE).append(System.lineSeparator());

      // main loop for looping through all layers
      int index = 0;
      Map<String, LayerInterface> allLayers = model.getLayers();
      Map<String, FilterInterface> allFilters = model.getAllFilters();
      PixelInterface[][] finalPixelGrid = null;
      for (Map.Entry<String, LayerInterface> layer : allLayers.entrySet()) {
        LayerInterface currentLayer = layer.getValue();
        FilterInterface currentFilter = allFilters.get(currentLayer.getFilter());
        LayerInterface filteredLayer = currentFilter.apply(currentLayer);
        if (index == 0) {
          finalPixelGrid = filteredLayer.getPixelGrid();
        }
        PixelInterface[][] curGrid = filteredLayer.getPixelGrid();
        this.compressLayer(height, width, curGrid, finalPixelGrid);
        index++;
      }
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          finalImage.append(finalPixelGrid[row][col].toString(1));
        }
      }
      this.imageWrite(imagePath, finalImage);
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

  private void imageWrite(String imagePath, StringBuilder finalImage) {
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(imagePath);
      fileWriter.write(finalImage.toString());
      fileWriter.close();
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
      //do something with the exception
    }//TODO: make filewriter class
  }
}
