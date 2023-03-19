package model;


/**
 * Represents the model for a Project in the Collager project. This interface allows for the user to
 * create a collage and includes the following.
 * <ul>
 * <li> creating a new project</li>
 * <li> adding layers to the project</li>
 * <li> adding filters to layers</li>
 * <li> adding images to layers</li>
 * </ul>
 */
public interface ProjectModel extends ProjectModelState {

  /**
   * Creates a new project of the size based on the given height and width.
   *
   * @param height the height of the canvas
   * @param width  the width of the canvas
   * @throws IllegalArgumentException if the provided height and width are negative, or too large
   *                                  for the computer screen
   */
  void newProject(int height, int width) throws IllegalArgumentException;

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
   * Adds the particular filter name to the given layer name.
   *
   * @param layerName  the layer name the filter will be added to
   * @param filterName the name of the filter
   * @throws IllegalArgumentException if the layer name or filter name provided is invalid
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
   * @param layerName      the name of the layer to add the image to
   * @param imagePixelGrid the image file represented as a 2D array of {@code Pixel}s
   * @param x              the x position offset
   * @param y              the y position offset
   * @throws IllegalArgumentException if layer name provided does not exist, the x position is
   *                                  negative or off the grid, and if the y position is negative or
   *                                  off the grid
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   */
  void addImageToLayer(String layerName, PixelInterface[][] imagePixelGrid, int x, int y)
      throws IllegalArgumentException, IllegalStateException;
}
