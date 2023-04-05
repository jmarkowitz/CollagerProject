package gui.controller;

import controller.file.FileHandler;
import controller.file.FileUtil;
import controller.file.ImageHandler;
import controller.file.PPMHandler;
import controller.file.TextProjectHandler;
import gui.view.GUIProjectView;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import model.PixelInterface;
import model.ProjectModel;

/**
 * Represents the features that the GUI will support. Allows the user to interact with the GUI, by
 * creating a project, loading a project, saving a project or image, and manipulating the layers.
 */
public class FeatureController implements Features {

  private final ProjectModel model;
  private final GUIProjectView view;
  public boolean isProjectActive;

  /**
   * Constructs a Controller object.
   *
   * @param model the model for the program
   * @param view  the view for the program
   * @throws IllegalArgumentException if the model or view is null
   */
  public FeatureController(ProjectModel model, GUIProjectView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and view cannot be null.");
    }
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }

  /**
   * Creates a new project with the given height and width.
   *
   * @param height the height of the project
   * @param width  the width of the project
   */
  @Override
  public void newProject(int height, int width) {
    if (!this.isProjectActive) {
      try {
        this.model.newProject(height, width);
        this.getFilters();
        view.renderImage(model.compressLayers());
        view.activateButtons();
        view.refresh();
        this.isProjectActive = true;
      } catch (IllegalArgumentException e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
      }
    } else {
      this.view.renderMessage(
          "A project is already active. Please restart the program to create a new project.");
    }
  }

  /**
   * Loads a project from the given filepath.
   *
   * @param filepath the filepath of the project
   */
  @Override
  public void loadProject(String filepath) {
    try {
      FileHandler<String> fileHandler = new TextProjectHandler(model);
      String entireProject = fileHandler.readFile(filepath);
      model.buildProject(entireProject);
      Set<String> allLayers = model.getLayers().keySet();
      for (String layerName : allLayers) {
        if (!layerName.equals("bg")) {
          view.addLayer(layerName);
        }
      }
      this.getFilters();
      view.renderImage(model.compressLayers());
      view.activateButtons();
      view.refresh();
      this.isProjectActive = true;
    } catch (IOException | IllegalStateException | IllegalArgumentException e) {
      view.renderMessage(e.getMessage());
    }

  }

  /**
   * Saves the current project to the given filepath.
   *
   * @param filepath the filepath of the project
   */
  @Override
  public void saveProject(String filepath) {
    try {
      FileHandler<String> projectHandler = new TextProjectHandler(model);
      projectHandler.writeFile(filepath);
      view.renderMessage("Project successfully saved to " + filepath);
      view.refresh();
    } catch (IOException e) {
      view.renderMessage(e.getMessage());
    }

  }

  /**
   * Saves the current project as an image to the given filepath.
   *
   * @param filepath the filepath of the image
   */
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
      view.refresh();
    } catch (IOException e) {
      view.renderMessage(e.getMessage());
    }
  }

  /**
   * Quits the program.
   */
  @Override
  public void quit() {
    System.exit(0);
  }

  /**
   * Adds a new layer to the project with the given name.
   *
   * @param layerName the name of the layer
   */
  @Override
  public void addLayer(String layerName) {
    try {
      model.addLayer(layerName);
      view.addLayer(layerName);
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }
  }

  /**
   * Adds an image to the given layer at the given coordinates.
   *
   * @param layerName the name of the layer
   * @param imagePath the filepath of the image
   * @param x         the x coordinate to place the image
   * @param y         the y coordinate to place the image
   */
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
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }

  }

  /**
   * Removes the given layer from the project.
   *
   * @param layerName the name of the layer
   */
  @Override
  public void setFilter(String layerName, String filterName) {
    try {
      model.setFilter(layerName, filterName);
      view.renderImage(model.compressLayers());
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage("Please select a layer to apply a filter to.");
    }

  }

  /**
   * Gets a list of the filters that are available.
   */
  @Override
  public void getFilters() {
    try {
      Set<String> filterSet = model.getAllFilters().keySet();
      view.addFilters(List.copyOf(filterSet));
      view.refresh();
    } catch (IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }
  }
}
