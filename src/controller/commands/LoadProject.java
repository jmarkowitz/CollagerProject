package controller.commands;

import controller.CollagerCommand;
import controller.FileUtil;
import java.io.IOException;
import java.util.Scanner;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModel;
import view.ProjectView;

public class LoadProject implements CollagerCommand {

  private final Scanner scanner;
  private final ProjectView view;

  public LoadProject(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }


  @Override
  public void execute(ProjectModel model) throws IOException {
    while (this.scanner.hasNext()) {
      String projectFilepath = this.scanner.next();
      String projectString = FileUtil.readFileAsString(projectFilepath);
      int width;
      int height;
      Scanner sc;
      sc = new Scanner(projectString);
      String token;
      token = sc.next();
      if (!token.equals("C1")) {
        throw new IllegalArgumentException(
            "Invalid Project file: plain RAW file should begin with C1");
      }
      width = sc.nextInt();
      height = sc.nextInt();
      try {
        model.newProject(height, width);
      } catch (IllegalArgumentException e) {
        this.view.renderMessage(e.getMessage());
        throw new IOException();
      }
      int maxValue = sc.nextInt();
      while (sc.hasNext()) {
        String layerName = sc.next();
        try {
          model.addLayer(layerName);
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage(e.getMessage());
          throw new IOException();
        }
        String filterName = sc.next();
        try {
          model.setFilter(layerName, filterName);
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage(e.getMessage());
          throw new IOException();
        }
        PixelInterface[][] currentLayer = new PixelInterface[height][width];
        for (int row = 0; row < height; row++) {
          for (int col = 0; col < width; col++) {
            int red = sc.nextInt();
            int green = sc.nextInt();
            int blue = sc.nextInt();
            int alpha = sc.nextInt();
            currentLayer[row][col] = new Pixel(this.scalePixel(red, maxValue),
                this.scalePixel(green, maxValue), this.scalePixel(blue, maxValue),
                this.scalePixel(alpha, maxValue));
          }
        }
        try {
          model.addImageToLayer(layerName, currentLayer, 0, 0);
          this.view.renderMessage("layer successfully loaded in" + System.lineSeparator());
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage(e.getMessage() + System.lineSeparator());
          throw new IOException();
        }
        break;
      }
    }
  }

  private int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }
}
