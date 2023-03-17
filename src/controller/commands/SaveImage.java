package controller.commands;

import static model.Project.MAX_VALUE;

import controller.CollagerCommand;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModel;

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
      finalImage.append("P3").append(System.lineSeparator());
      finalImage.append(model.getWidth()).append(" ").append(model.getHeight())
          .append(System.lineSeparator());
      finalImage.append(MAX_VALUE).append(System.lineSeparator());

      int index = 0;
      // main loop for looping through all layers
      Map<String, LayerInterface> allLayers = model.getLayers();
      for (Map.Entry<String, LayerInterface> layer : allLayers.entrySet()) {
        LayerInterface currentLayer = layer.getValue();
        if (!currentLayer.getFilter().getFilterName().equals("Normal")) { // do not apply filter for "Normal" layers
          LayerInterface filteredLayer = currentLayer.getFilter().apply(currentLayer);
          currentLayer = filteredLayer;
        }
        PixelInterface[][] currentPixelGrid = currentLayer.getPixelGrid();
        this.pixelMath(model, finalImage, index, currentPixelGrid);
        index++;
      }
      this.imageWrite(imagePath, finalImage);
    }
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

  private void pixelMath(ProjectModel model, StringBuilder finalImage, int index,
      PixelInterface[][] currentPixelGrid) {
    int a0;
    int a1 = 0;
    int r1 = 0;
    int g1 = 0;
    int b1 = 0;
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getHeight(); col++) {
        int r = currentPixelGrid[row][col].getRed();
        int g = currentPixelGrid[row][col].getGreen();
        int b = currentPixelGrid[row][col].getBlue();
        int a = currentPixelGrid[row][col].getAlpha();
        if (index == 0) {
          a1 = a;
          r1 = r;
          g1 = g;
          b1 = b;
        }
        if (index != 0) {
          int aPrev = a1; //previous a
          a0 = (a / MAX_VALUE + a1 / MAX_VALUE * (1 - a / MAX_VALUE));
          a1 = a0 * MAX_VALUE;  //calculated a
          r1 = (a / MAX_VALUE * r + r1 * (aPrev / MAX_VALUE) * (1 - a / MAX_VALUE)) * (1 / a0);
          g1 = (a / MAX_VALUE * g + g1 * (aPrev / MAX_VALUE) * (1 - a / MAX_VALUE)) * (1 / a0);
          b1 = (a / MAX_VALUE * b + b1 * (aPrev / MAX_VALUE) * (1 - a / MAX_VALUE)) * (1 / a0);
        }
        PixelInterface finalPixel = new Pixel(r1, g1, b1, a1).convertToRGB();
        finalImage.append(finalPixel.toString(1));
      }
    }
  }
}
