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

  private final Scanner scanner;
  private final ProjectView view;

  /**
   * Constructor for the load project command.
   *
   * @param scanner the scanner that will be used to read the file
   * @param view    the view that will be used to render the message
   */
  public LoadProject(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }

  /**
   * Method that will execute the load project command.
   *
   * @param model the model that will be used to load the project
   * @throws IOException              if there is an issue rendering the message
   * @throws IllegalArgumentException if the file is invalid
   */
  @Override
  public void execute(ProjectModel model) throws IOException, IllegalArgumentException {
    boolean isBGLayer = true;
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
        this.view.renderMessage("loaded project successfully created" + System.lineSeparator());
      } catch (IllegalArgumentException e) {
        this.view.renderMessage(e.getMessage());
        throw new IOException("Could not create new project");
      }
      int maxValue = sc.nextInt();
      while (sc.hasNext()) {
        String layerName = sc.next();
        try {
          if (!isBGLayer) {
            model.addLayer(layerName);
            this.view.renderMessage(
                layerName + " layer successfully loaded in" + System.lineSeparator());

          }
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage(e.getMessage());
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
          this.view.renderMessage(e.getMessage() + System.lineSeparator());
          throw new IOException("Could not add image to layer");
        }
        try {
          if (!isBGLayer) {
            model.setFilter(layerName, filterName);
            this.view.renderMessage(filterName + " filter successfully set on layer " + layerName
                + System.lineSeparator());
          }
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage(e.getMessage());
          throw new IOException("Could not set filter");
        }
        isBGLayer = false;
      }
      break;
    }
  }

  private int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }
}
