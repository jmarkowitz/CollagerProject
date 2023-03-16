package model;

import java.io.FileNotFoundException;

/**
 * Represents the model for a Project in the Collager project. This interface allows for the user to
 * create a collage and includes the following.
 * <ul>
 * <li> creating a new project</li>
 * <li> adding layers to the project</li>
 * <li> adding images to layers</li>
 * <li> saving images</li>
 * <li> saving projects</li>
 * <li> loading projects from a file</li>
 * </ul>
 */
public interface ProjectModel extends ProjectModelState {

  /**
   * Creates a new project of the size based on the given height and width.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @throws IllegalArgumentException if the provided height and width are negative, or too large
   *                                  for the computer screen
   */
  void newProject(int width, int height) throws IllegalArgumentException;

  /**
   * Allows a layer to be added to the list of layers contained in the project if it does not exist
   * in the layer list yet.
   *
   * @param layerName the layer to be added
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   * @throws IllegalArgumentException if the layer provided already exists
   */
  void addLayer(String layerName) throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds the particular filter name to the given layer name
   *
   * @param layerName  the layer name the filter will be added to
   * @param filterName the name of the filter
   * @throws IllegalArgumentException if the layer or filter name provided is invalid
   * @throws IllegalStateException    if this method is called before a project has been created or
   *                                  loaded
   */
  void setFilter(String layerName, String filterName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds an image to the specific layer provided based on the image path and will place the top
   * left corner of the image at the given x and y position. If the image provided is larger than
   * the height and width of the project, it will import only the part that can fit and the rest
   * will be cut off.
   *
   * @param layerName the name of the layer to add the image to
   * @param imagePath the filepath of the image
   * @param x         the x position offset
   * @param y         the y position offset
   * @throws FileNotFoundException    if the filepath of the image provided does not exist
   * @throws IllegalArgumentException if layer name provided does not exist, the x position is
   *                                  negative or off the grid, and if the y position is negative or
   *                                  off the grid
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   */
  void addImageToLayer(String layerName, String imagePath, int x, int y)
      throws FileNotFoundException, IllegalArgumentException, IllegalStateException;

  /**
   * Applies each layer's respective filter and combines all layers into one image.
   *
   * @param filepath the filepath and name to save the image to
   * @throws IllegalStateException    if this method is called before a new project is created or
   *                                  loaded in
   * @throws IllegalArgumentException if the filepath provided is invalid
   */
  void saveImage(String filepath) throws IllegalStateException, IllegalArgumentException;

  /**
   * Loads a collage project from the provided filepath and returns a {@code ProjectModel}.
   *
   * @param filepath the filepath of the project to be loaded in
   * @throws FileNotFoundException if the filepath provided does not exist
   * @throws IllegalStateException if this method is called while a project is currently being
   *                               worked on
   */
  void loadProject(String filepath)
      throws FileNotFoundException, IllegalStateException; //TODO: should this be here?

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
   * @throws IllegalStateException if this method is called before a project has been created or
   *                               loaded
   */
  void saveProject(String filepath) throws IllegalStateException;

}
