package model;

/**
 * Represents a collection of pixels that has a name and a filter associated with it.
 */
public class Layer implements LayerInterface {

  private final String layerName;
  private String filter;
  private PixelInterface[][] grid;

  /**
   * Constructs a layer with the given name, and assigns the default filter "normal" to it.
   *
   * @param layerName the name of the layer
   */
  public Layer(String layerName) {
    this.layerName = layerName;
    this.filter = "normal";
  }

  /**
   * Constructs a layer with the given name and grid and also assigns the default filter "normal" to
   * it.
   *
   * @param layerName the name of the layer
   * @param grid      the 2D array of pixels that make up the layer
   */
  public Layer(String layerName, PixelInterface[][] grid) {
    this.layerName = layerName;
    this.filter = "normal";
    this.grid = grid;
  }

  /**
   * Returns the name of the layer.
   *
   * @return the name of the layer
   */
  @Override
  public String getName() {
    return this.layerName;
  }

  /**
   * Returns the name of the filter associated with the layer.
   *
   * @return the name of the filter
   */
  @Override
  public String getFilter() {
    return this.filter;
  }

  /**
   * Sets the name of the layer.
   *
   * @param filterOption the name of the layer
   */
  @Override
  public void setFilter(String filterOption) {
    this.filter = filterOption;
  }

  /**
   * Returns a copy of the 2D array of pixels that make up the layer.
   *
   * @return a 2D array of pixels
   */
  @Override
  public PixelInterface[][] getPixelGrid() {
    return this.grid.clone();
  }
}
