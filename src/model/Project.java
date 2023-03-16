package model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Project implements ProjectModel {

  public static final int MAX_VALUE = 255;
  private PixelInterface[][] transparentWhiteGrid;
  private PixelInterface[][] opaqueWhiteGrid;
  private final int screenWidth;
  private final int screenHeight;
  private int height;
  private int width;
  private final Map<String, LayerInterface> layerLinkedMap;
  private boolean inProgress;
  //TODO: might need more flags


  /**
   * Constructs a new project.
   */
  public Project() {
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    this.screenWidth = (int) size.getWidth();
    this.screenHeight = (int) size.getHeight();
    this.layerLinkedMap = new LinkedHashMap<>();
  }

  // Options:
  //  - 0: Transparent (alpha = 0)
  //  - 1: Opaque (alpha = 255)
  private PixelInterface[][] makeTransparentOrOpaqueWhiteLayer(int option) {
    int alpha = 0;
    if (option == 1) {
      alpha = 255;
    }
    PixelInterface[][] current = new Pixel[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        current[row][col] = new Pixel(255, 255, 255, alpha);
      }
    }
    return current;
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
    if (width < 0) {
      throw new IllegalArgumentException("Invalid width provided");
    } else if (height < 0) {
      throw new IllegalArgumentException("Invalid height provided");
    } else if (width > this.screenWidth) {
      throw new IllegalArgumentException("Invalid width provided for screen size");
    } else if (height > this.screenHeight) {
      throw new IllegalArgumentException("Invalid height provided for screen size");
    }
    this.width = width;
    this.height = height;
    this.inProgress = true;
    this.transparentWhiteGrid = this.makeTransparentOrOpaqueWhiteLayer(0);
    this.opaqueWhiteGrid = this.makeTransparentOrOpaqueWhiteLayer(1);
    this.layerLinkedMap.put("bg", new Layer("bg", this.opaqueWhiteGrid));
  }

  /**
   * Allows a layer to be added to the list of layers contained in the project if it does not exist
   * in the layer list yet.
   *
   * @param layerName the layer to be added
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   * @throws IllegalArgumentException if the layer provided already exists
   */
  @Override
  public void addLayer(String layerName)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot add layer until new project has been created");
    } else if (this.layerLinkedMap.containsKey(layerName)) {
      throw new IllegalArgumentException("Layer name provided already exists");
    }
    this.layerLinkedMap.put(layerName, new Layer(layerName, this.transparentWhiteGrid));

  }

  /**
   * Adds the particular filter name to the given layer name
   *
   * @param layerName  the layer name the filter will be added to
   * @param filterName the name of the filter
   * @throws IllegalArgumentException if the layer name provided is invalid
   * @throws IllegalStateException    if this method is called before a project has been created or
   *                                  loaded
   */
  @Override
  public void setFilter(String layerName, String filterName)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot add layer until new project has been created");
    } else if (!this.layerLinkedMap.containsKey(layerName)) {
      throw new IllegalArgumentException("Layer name provided does not exist");
    }
    this.layerLinkedMap.get(layerName).setFilter(filterName);
  }//TODO: I don't like this but we need a way to add filters to layers but what if filter provided doesn't exist? Handled in the controller?

//  private boolean layerNameExists(String layerName) {
//    for (LayerInterface layer : this.layerLinkedMap) {
//      return (layer.getName().equals(layerName));
//    }
//    return false;
//  } //TODO: delete or reuse this

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
   * @throws IllegalArgumentException if layer name provided does not exist, the x position is
   *                                  negative or off the grid, and if the y position is negative or
   *                                  off the grid
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   */
  @Override
  public void addImageToLayer(String layerName, String imagePath, int x, int y)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.inProgress) {
      throw new IllegalStateException(
          "Cannot add image to layer until new project has been created");
    } else if (!this.layerLinkedMap.containsKey(layerName)) {
      throw new IllegalArgumentException("Cannot add image to layer that does not exist");
    } else if (x < this.width || x > this.width) {
      throw new IllegalArgumentException("Invalid x coordinate to place image");
    } else if (y < this.height || y > this.height) {
      throw new IllegalArgumentException("Invalid y coordinate to place image");
    } //TODO: determine if we need to handle invalid imagePath provided or will filereader handle that?

    LayerInterface layerCurrent = this.layerLinkedMap.get(layerName);
    PixelInterface[][] layerPixelGrid = layerCurrent.getPixelGrid();
    PixelInterface[][] imagePixelGrid = fileReader.readImage(imagePath); //TODO: finish file reader
    PixelInterface[][] updatedLayerGrid = this.changePixels(layerPixelGrid, imagePixelGrid);
    LayerInterface updatedLayer = new Layer(layerName, updatedLayerGrid);
    this.layerLinkedMap.replace(layerName, updatedLayer);
  }

  private PixelInterface[][] changePixels(PixelInterface[][] layerGrid,
      PixelInterface[][] imageGrid) {
    for (int row = 0; row < this.width; row++) {
      for (int col = 0; col < this.height; col++) {
        layerGrid[row][col] = imageGrid[row][col];
      }
    }
    return layerGrid;
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
    if (!this.inProgress) {
      throw new IllegalStateException(
          "Cannot add image to layer until new project has been created");
      //TODO: finish implementing method
    }
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
    //TODO: finish implementing method
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
   * @throws IllegalArgumentException if the filepath provided cannot be written to or is invalid
   * @throws IllegalStateException if this method is called before a project has been created or
   *                               loaded
   */
  @Override
  public void saveProject(String filepath) throws IllegalStateException {
    //TODO: finish implementing method

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
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot get width until new project has been created");
    }
    return this.width;
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
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot get height until new project has been created");
    }
    return this.height;
  }

  /**
   * Returns a cloned list of all the layers currently in the canvas
   *
   * @return the Map of all layers in the canvas
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  @Override
  public Map<String, LayerInterface> getLayers() throws IllegalStateException {
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot get layer list until new project has been created");
    }
    return new LinkedHashMap<>(this.layerLinkedMap);
  }
}
