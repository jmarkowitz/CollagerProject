package model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedHashMap;
import java.util.Map;
import model.filters.BlueFilter;
import model.filters.BrightenIntensity;
import model.filters.BrightenLuma;
import model.filters.BrightenValue;
import model.filters.DarkenIntensity;
import model.filters.DarkenLuma;
import model.filters.DarkenValue;
import model.filters.GreenFilter;
import model.filters.RedFilter;
//TODO: write javadocs
public class Project implements ProjectModel {

  public static final int MAX_VALUE = 255;
  private final int screenWidth;
  private final int screenHeight;
  private int height;
  private int width;
  private final Map<String, LayerInterface> layerLinkedMap;
  private boolean inProgress;


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
   * @param height the height of the canvas
   * @param width  the width of the canvas
   * @throws IllegalArgumentException if the provided height and width are negative, or too large
   *                                  for the computer screen
   */
  @Override
  public void newProject(int height, int width) throws IllegalArgumentException {
    if (height < 0) {
      throw new IllegalArgumentException("Invalid width provided");
    } else if (width < 0) {
      throw new IllegalArgumentException("Invalid height provided");
    } else if (height > this.screenWidth) {
      throw new IllegalArgumentException("Invalid width provided for screen size");
    } else if (width > this.screenHeight) {
      throw new IllegalArgumentException("Invalid height provided for screen size");
    }
    this.height= height;
    this.width = width;
    this.inProgress = true;
    this.layerLinkedMap.put("bg", new Layer("bg", this.makeTransparentOrOpaqueWhiteLayer(0)));
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
    this.layerLinkedMap.put(layerName, new Layer(layerName, this.makeTransparentOrOpaqueWhiteLayer(1)));
  }

  /**
   * Adds the particular filter name to the given layer name
   *
   * @param layerName  the layer name the filter will be added to
   * @param filterName the name of the filter
   * @throws IllegalArgumentException if the layer name or filter name provided is invalid
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
    switch (filterName) {
      case "red-component":
        this.layerLinkedMap.get(layerName).setFilter(new RedFilter(this.height, this.width));
        break;
      case "blue-component":
        this.layerLinkedMap.get(layerName).setFilter(new BlueFilter(this.height, this.width));
        break;
      case "green-component":
        this.layerLinkedMap.get(layerName).setFilter(new GreenFilter(this.height, this.width));
        break;
      case "brighten-luma":
        this.layerLinkedMap.get(layerName).setFilter(new BrightenLuma(this.height, this.width));
        break;
      case "darken-luma":
        this.layerLinkedMap.get(layerName).setFilter(new DarkenLuma(this.height, this.width));
        break;
      case "brighten-intensity":
        this.layerLinkedMap.get(layerName)
            .setFilter(new BrightenIntensity(this.height, this.width));
        break;
      case "darken-intensity":
        this.layerLinkedMap.get(layerName).setFilter(new DarkenIntensity(this.height, this.width));
        break;
      case "brighten-value":
        this.layerLinkedMap.get(layerName).setFilter(new BrightenValue(this.height, this.width));
        break;
      case "darken-value":
        this.layerLinkedMap.get(layerName).setFilter(new DarkenValue(this.height, this.width));
        break;
      default:
        throw new IllegalArgumentException("Invalid filter name provided");

    }
  }

  /**
   * Adds an image to the specific layer provided based on the image path and will place the top
   * left corner of the image at the given x and y position. If the image provided is larger than
   * the height and width of the project, it will import only the part that can fit and the rest
   * will be cut off.
   *
   * @param layerName      the name of the layer to add the image to
   * @param imagePixelGrid the filepath of the image
   * @param x              the x position offset
   * @param y              the y position offset
   * @throws IllegalArgumentException if layer name provided does not exist, the x position is
   *                                  negative or off the grid, and if the y position is negative or
   *                                  off the grid
   * @throws IllegalStateException    if this method is called before a project is created or loaded
   *                                  in
   */
  @Override
  public void addImageToLayer(String layerName, PixelInterface[][] imagePixelGrid, int x, int y)
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
    PixelInterface[][] updatedLayerGrid = this.changePixels(layerPixelGrid, imagePixelGrid, x, y);
    LayerInterface updatedLayer = new Layer(layerName, updatedLayerGrid);
    this.layerLinkedMap.replace(layerName, updatedLayer);
  }

  private PixelInterface[][] changePixels(PixelInterface[][] layerGrid,
      PixelInterface[][] imageGrid, int x, int y) {
    for (int row = y; row < this.width; row++) {
      for (int col = x; col < this.height; col++) {
        layerGrid[row][col] = imageGrid[row][col];
      }
    }
    return layerGrid;
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
