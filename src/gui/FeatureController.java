package gui;

import controller.file.FileHandler;
import controller.file.FileUtil;
import controller.file.ImageHandler;
import controller.file.PPMHandler;
import controller.file.TextProjectHandler;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import model.PixelInterface;
import model.ProjectModel;

/**
 * Represents the controller for the GUI. This class will handle all of the user input, and update the view accordingly.
 */
public class FeatureController implements Features {

  private ProjectModel model;
  private GUIProjectView view;
  public boolean isProjectActive;

  /**
   * Constructs a Controller object.
   * @param model the model for the program
   * @param view the view for the program
   */
  public FeatureController(ProjectModel model, GUIProjectView view) {
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void newProject(int height, int width) {
    if (!this.isProjectActive) {
      try {
        this.model.newProject(height, width);
        this.getFilters();
        view.renderImage(model.compressLayers());
        view.activateButtons();
        this.isProjectActive = true;
      } catch (IllegalArgumentException e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
      }
    } else {
      this.view.renderMessage("A project is already active. Please restart the program to create a new project.");
    }
  }

  @Override
  public void loadProject(String filepath) {
    try {
      FileHandler<String> fileHandler = new TextProjectHandler(model);
      String entireProject = fileHandler.readFile(filepath);
      model.buildProject(entireProject);
    } catch (IOException | IllegalStateException | IllegalArgumentException e) {
      view.renderMessage(e.getMessage());
    }

  }

  @Override
  public void saveProject(String filepath) {

  }

  @Override
  public void saveImageAs(String filepath) {
    FileHandler<PixelInterface[][]> imageHandler = null;
    String fileExt = FileUtil.getFileExtension(filepath);
    switch (fileExt) {
      case "ppm":
        imageHandler = new PPMHandler(model);
        break;
      case "jpg":
      case "jpeg":
      case "png":
        imageHandler = new ImageHandler(model);
        break;
      default:
        view.renderMessage("Invalid file extension" + System.lineSeparator());
    }
    try {
      imageHandler.writeFile(filepath);
      view.renderMessage("Image successfully saved to " + filepath);
    } catch (IOException e) {
      view.renderMessage(e.getMessage());
    }
  }

  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public void addLayer(String layerName) {
    try {
      model.addLayer(layerName);
      view.addLayer(layerName);
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }
  }

  @Override
  public void addImageToLayer(String layerName, String imagePath, int x, int y) {
    FileHandler<PixelInterface[][]> imageHandler = null;
    String fileExt = FileUtil.getFileExtension(imagePath);
    switch (fileExt) {
      case "ppm":
        imageHandler = new PPMHandler(model);
        break;
      case "jpg":
      case "jpeg":
      case "png":
        imageHandler = new ImageHandler(model);
        break;
      default:
        view.renderMessage("Invalid file extension" + System.lineSeparator());
    }
    PixelInterface[][] image = null;
    try {
      image = imageHandler.readFile(imagePath);
    } catch (IOException e) {
      view.renderMessage(e.getMessage());
    }
    try {
      model.addImageToLayer(layerName, image, x, y);
      view.renderImage(model.compressLayers());
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }

  }

  @Override
  public void setFilter(String layerName, String filterName) {
    try {
      model.setFilter(layerName, filterName);
      view.renderImage(model.compressLayers());
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage("Please select a layer to apply a filter to.");
    }

  }

  /**
   * Returns a list of the filters that are available.
   *
   */
  @Override
  public void getFilters() {
    try {
      Set<String> filterSet = model.getAllFilters().keySet();
      view.addFilters(List.copyOf(filterSet));
    } catch (IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }
  }
}
