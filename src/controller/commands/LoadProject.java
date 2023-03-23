package controller.commands;

import controller.CollagerCommand;
import controller.FileUtil;
import java.io.IOException;
import java.util.Scanner;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModel;
import view.ProjectView;

/**
 * Class that represents the load project command.
 */
public class LoadProject implements CollagerCommand {

  private final String filepath;

  /**
   * Constructs a load project command
   *
   * @param filepath the filepath to load the project from
   */
  public LoadProject(String filepath) {
    this.filepath = filepath;
  }

  /**
   * Method that will execute the load project command.
   *
   * @param model the model that will be used to load the project
   * @param view
   * @throws IOException              if there is an issue rendering the message
   * @throws IllegalArgumentException if the file is invalid
   */
  @Override
  public void execute(ProjectModel model, ProjectView view)
      throws IOException, IllegalArgumentException {
    boolean isBGLayer = true;
    String projectString = FileUtil.readFileAsString(this.filepath);
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
      view.renderMessage("loaded project successfully created" + System.lineSeparator());
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage());
      throw new IOException("Could not create new project");
    }
    int maxValue = sc.nextInt();
    while (sc.hasNext()) {
      String layerName = sc.next();
      try {
        if (!isBGLayer) {
          model.addLayer(layerName);
          view.renderMessage(
              layerName + " layer successfully loaded in" + System.lineSeparator());

        }
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.renderMessage(e.getMessage());
        throw new IOException("Could not add layer");
      }
      String filterName = sc.next();
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
        if (!isBGLayer) {
          model.addImageToLayer(layerName, currentLayer, 0, 0);
          this.view.renderMessage("layer successfully loaded in" + System.lineSeparator());
        }
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException("Could not add image to layer");
      }
      try {
        if (!isBGLayer) {
          model.setFilter(layerName, filterName);
          view.renderMessage(filterName + " filter successfully set on layer " + layerName
              + System.lineSeparator());
        }
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.renderMessage(e.getMessage());
        throw new IOException("Could not set filter");
      }
      isBGLayer = false;
    }
  }

  private int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }
}
