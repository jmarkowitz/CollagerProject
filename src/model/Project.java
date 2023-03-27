package model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import model.filters.BlueFilter;
import model.filters.BrightenIntensityFilter;
import model.filters.BrightenLumaFilter;
import model.filters.BrightenValueFilter;
import model.filters.ScreenFilter;
import model.filters.DarkenIntensityFilter;
import model.filters.DarkenLumaFilter;
import model.filters.DarkenValueFilter;
import model.filters.MultiplyFilter;
import model.filters.GreenFilter;
import model.filters.DifferenceFilter;
import model.filters.NormalFilter;
import model.filters.RedFilter;

/**
 * Represents a collager project that can be created and edited. Allows the user to create a new
 * project, add layers and images, and set filters.
 */
public class Project implements ProjectModel {

  public static final int MAX_VALUE = 255;
  private final int screenWidth;
  private final int screenHeight;
  private int height;
  private int width;
  private final Map<String, LayerInterface> layerLinkedMap;
  private final Map<String, FilterInterface> allFilters;
  private boolean inProgress;


  /**
   * Constructs a new project.
   */
  public Project() {
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    this.screenWidth = (int) size.getWidth();
    this.screenHeight = (int) size.getHeight();
    this.layerLinkedMap = new LinkedHashMap<>();
    this.allFilters = new HashMap<>();
  }

  private void initFilters() {
    this.allFilters.put("red-component", new RedFilter(this.height, this.width));
    this.allFilters.put("green-component", new GreenFilter(this.height, this.width));
    this.allFilters.put("blue-component", new BlueFilter(this.height, this.width));
    this.allFilters.put("brighten-luma", new BrightenLumaFilter(this.height, this.width));
    this.allFilters.put("darken-luma", new DarkenLumaFilter(this.height, this.width));
    this.allFilters.put("brighten-intensity", new BrightenIntensityFilter(this.height, this.width));
    this.allFilters.put("darken-intensity", new DarkenIntensityFilter(this.height, this.width));
    this.allFilters.put("brighten-value", new BrightenValueFilter(this.height, this.width));
    this.allFilters.put("darken-value", new DarkenValueFilter(this.height, this.width));
    this.allFilters.put("normal", new NormalFilter(this.height, this.width));
    this.allFilters.put("difference", new DifferenceFilter(this.height, this.width));
    this.allFilters.put("multiply", new MultiplyFilter(this.height, this.width));
    this.allFilters.put("screen", new ScreenFilter(this.height, this.width));
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
    if (height <= 0) {
      throw new IllegalArgumentException("Invalid width provided");
    } else if (width <= 0) {
      throw new IllegalArgumentException("Invalid height provided");
    } else if (height > this.screenWidth) {
      throw new IllegalArgumentException("Invalid width provided for screen size");
    } else if (width > this.screenHeight) {
      throw new IllegalArgumentException("Invalid height provided for screen size");
    }
    this.height = height;
    this.width = width;
    this.inProgress = true;
    this.initFilters();
    this.layerLinkedMap.put("bg", new Layer("bg", this.makeTransparentOrOpaqueWhiteLayer(1)));
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
    this.layerLinkedMap.put(layerName,
        new Layer(layerName, this.makeTransparentOrOpaqueWhiteLayer(0)));
  }

  /**
   * Adds the particular filter name to the given layer name.
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
    this.layerLinkedMap.get(layerName).setFilter(filterName);
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
    } else if (x < 0 || x >= this.width) {
      throw new IllegalArgumentException("Invalid x coordinate to place image");
    } else if (y < 0 || y >= this.height) {
      throw new IllegalArgumentException("Invalid y coordinate to place image");
    }

    LayerInterface layerCurrent = this.layerLinkedMap.get(layerName);
    PixelInterface[][] layerPixelGrid = layerCurrent.getPixelGrid();
    PixelInterface[][] updatedLayerGrid = this.changePixels(layerPixelGrid, imagePixelGrid, x, y);
    LayerInterface updatedLayer = new Layer(layerName, updatedLayerGrid);
    this.layerLinkedMap.replace(layerName, updatedLayer);
  }

  private PixelInterface[][] changePixels(PixelInterface[][] bottomImageGrid,
      PixelInterface[][] topImageGrid, int x, int y) {
    int imageHeight = topImageGrid.length;
    int imageWidth = topImageGrid[0].length;

    int xEnd = Math.min(this.width - x, imageWidth);
    int yEnd = Math.min(this.height - y, imageHeight);

    for (int row = 0; row < yEnd; row++) {
      for (int col = 0; col < xEnd; col++) {
        PixelInterface bottomPixelGrid = bottomImageGrid[row + y][col + x];
        PixelInterface topPixelGrid = topImageGrid[row][col];
        PixelInterface updatedPixel = topPixelGrid.bgPixelConverter(bottomPixelGrid.getRed(), bottomPixelGrid.getGreen(), bottomPixelGrid.getBlue(), bottomPixelGrid.getAlpha());
        bottomImageGrid[row + y][col + x] = updatedPixel;
      }
    }
    return bottomImageGrid;
  }

  @Override
  public PixelInterface[][] compressLayers() throws IllegalStateException {
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot compress layers until new project has been created");
    }
    PixelInterface[][] bgImage = this.layerLinkedMap.get("bg").getPixelGrid();
    Set<String> allLayerNames = this.layerLinkedMap.keySet();
    for (String name : allLayerNames) {
      LayerInterface curLayer = layerLinkedMap.get(name);
      PixelInterface[][] curLayerGrid = curLayer.getPixelGrid();
      PixelInterface[][] curLayerFiltered = this.allFilters.get(curLayer.getFilter()).apply(curLayerGrid, bgImage);
      this.changePixels(bgImage, curLayerFiltered, 0, 0);
    }
    return bgImage;
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
   * Returns a cloned list of all the layers currently in the canvas.
   *
   * @return the Map of all layers in the canvas
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  @Override
  public Map<String, LayerInterface> getLayers() throws IllegalStateException {
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot get all layers until new project has been created");
    }
    return new LinkedHashMap<>(this.layerLinkedMap);
  }

  /**
   * Returns a copy of all filters available in the project.
   *
   * @return all the filters in the canvas
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  @Override
  public Map<String, FilterInterface> getAllFilters() throws IllegalStateException {
    if (!this.inProgress) {
      throw new IllegalStateException("Cannot get all filters until new project has been created");
    }
    return new HashMap<>(this.allFilters);
  }

  /**
   * Returns a string representation of the project.
   * @return the string representation of the project
   * @throws IllegalStateException if this method is called before a project is created or loaded in
   */
  @Override
  public String exportProject() throws IllegalStateException {
    StringBuilder finalString = new StringBuilder();
    finalString.append("C1").append(System.lineSeparator());
    finalString.append(width).append(" ").append(height).append(System.lineSeparator());
    finalString.append("255").append(System.lineSeparator());
    for (Map.Entry<String, LayerInterface> mapLayer : this.layerLinkedMap.entrySet()) {
      LayerInterface currentLayer = mapLayer.getValue();
      String currentLayerName = mapLayer.getKey();
      finalString.append(currentLayerName).append(" ").append(currentLayer.getFilter())
          .append(System.lineSeparator());
      PixelInterface[][] curLayerPixels = currentLayer.getPixelGrid();
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          finalString.append(curLayerPixels[row][col].toString(0)).append(System.lineSeparator());
        }
      }
    }
    return finalString.toString();
  }

  /**
   * Builds the project based on the given project string.
   * @param projectString the string representation of the project
   * @throws IllegalStateException if this method is called before a project is created or loaded in
   * @throws IllegalArgumentException if the project string is invalid
   */
  @Override
  public void buildProject(String projectString) throws IllegalStateException, IllegalArgumentException {
    boolean isBGLayer = true;
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
      this.newProject(height, width);
    int maxValue = sc.nextInt();
    while (sc.hasNext()) {
      String layerName = sc.next();
        if (!isBGLayer) {
          this.addLayer(layerName);
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
        if (!isBGLayer) {
          this.addImageToLayer(layerName, currentLayer, 0, 0);
        }
        if (!isBGLayer) {
          this.setFilter(layerName, filterName);
        }
      isBGLayer = false;
    }
  }

  private int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }
}
