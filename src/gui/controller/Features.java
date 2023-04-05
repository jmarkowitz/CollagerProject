package gui.controller;


/**
 * Represents the features that the GUI will support. Allows the user to interact with the GUI, by
 * creating a project, loading a project, saving a project or image, and manipulating the layers.
 */
public interface Features {

  /**
   * Creates a new project with the given height and width.
   *
   * @param height the height of the project
   * @param width  the width of the project
   */
  void newProject(int height, int width);

  /**
   * Loads a project from the given filepath.
   *
   * @param filepath the filepath of the project
   */
  void loadProject(String filepath);

  /**
   * Saves the current project to the given filepath.
   *
   * @param filepath the filepath of the project
   */
  void saveProject(String filepath);


  /**
   * Saves the current project as an image to the given filepath.
   *
   * @param filepath the filepath of the image
   */
  void saveImageAs(String filepath);

  /**
   * Adds a new layer to the project with the given name.
   *
   * @param layerName the name of the layer
   */
  void addLayer(String layerName);

  /**
   * Adds an image to the given layer at the given coordinates.
   *
   * @param layerName the name of the layer
   * @param imagePath the filepath of the image
   * @param x         the x coordinate to place the image
   * @param y         the y coordinate to place the image
   */
  void addImageToLayer(String layerName, String imagePath, int x, int y);

  /**
   * Removes the given layer from the project.
   *
   * @param layerName the name of the layer
   */
  void setFilter(String layerName, String filterName);

  /**
   * Gets a list of the filters that are available.
   */
  void getFilters();

  /**
   * Quits the program.
   */
  void quit();


}
