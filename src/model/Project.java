package model;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class Project implements ProjectModel {

  public static final int MAX_VALUE = 255;
  private int height;
  private int width;
  private List<Layer> layerStack;

  private boolean inProgress;
  //TODO: might need more flags


  /**
   * Constructs a new project.
   */
  public Project() {
  }

  /**
   * Creates a new project of the size based on the given height and width.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @throws IllegalArgumentException if the provided height and width are negative, or too large
   *                                  for the computer screen
   */
  @Override
  public void newProject(int width, int height) throws IllegalArgumentException {
    this.inProgress = true;

  }

  /**
   * Allows a layer to be added to the list of layers contained in the project if it is not null.
   *
   * @param layer the layer to be added
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   * @throws IllegalArgumentException if the layer provided is null
   */
  @Override
  public void addLayer(LayerInterface layer)
      throws IllegalArgumentException, IllegalStateException {

  }

  /**
   * Adds an image to the specific layer provided based on the image path and will place the top
   * left corner of the image at the given x and y position.
   *
   * @param layerName the name of the layer to add the image to
   * @param imagePath the filepath of the image
   * @param x         the x position offset
   * @param y         the y position offset
   * @throws IllegalArgumentException if layer name provided does not exist, the x position is
   *                                  negative or off the grid, and if the y position is negative or
   *                                  off the grid
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   */
  @Override
  public void addImageToLayer(String layerName, String imagePath, int x, int y)
      throws IllegalArgumentException, IllegalStateException {

  }

  /**
   * Applies each layer's respective filter and combines all layers into one image.
   *
   * @param filepath the filepath and name to save the image to
   * @throws IllegalStateException    if this method is called before a new project is created or
   *                                  loaded in
   * @throws IllegalArgumentException if the filepath provided is invalid
   */
  @Override
  public void saveImage(String filepath) throws IllegalStateException, IllegalArgumentException {

  }

  /**
   * Loads a collage project from the provided filepath and returns a {@code ProjectModel}.
   *
   * @param filepath the filepath of the project to be loaded in
   * @throws IllegalStateException if this method is called while a project is currently being
   *                               worked on
   */
  @Override
  public void loadProject(String filepath) throws IllegalStateException {
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
   * @param filepath the filepath to save the project to
   * @throws IllegalStateException      if this method is called before a project has been created
   *                                    or loaded
   */
  @Override
  public void saveProject(String filepath) throws IllegalStateException {

  }

  /**
   * Returns the width as an integer in pixel units representing the width of the canvas.
   *
   * @return the width of the pixel grid
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  @Override
  public int getWidth() throws IllegalStateException {
    return 0;
  }

  /**
   * Returns the height as an integer in pixel units representing the height of the canvas.
   *
   * @return the height of the pixel grid
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  @Override
  public int getHeight() throws IllegalStateException {
    return 0;
  }

  /**
   * Returns a cloned list of all the layers currently in the canvas
   *
   * @return the list of all layers in the canvas
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  @Override
  public List<Layer> getLayers() throws IllegalStateException {
    return null;
  }
}
