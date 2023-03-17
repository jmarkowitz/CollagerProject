package model;

public class Layer implements LayerInterface {

  private final String layerName;
  private FilterInterface filterName;
  private PixelInterface[][] grid;

  public Layer(String layerName) {
    this.layerName = layerName;
    this.filterName = null;
  }

  public Layer(String layerName, PixelInterface[][] grid) {
    this.layerName = layerName;
    this.grid = grid;
  }

  @Override
  public String getName() {
    return this.layerName;
  }

  @Override
  public FilterInterface getFilter() {
    return this.filterName;
  }

  @Override
  public void setFilter(FilterInterface filterOption) {
    this.filterName = filterOption;
  }

  @Override
  public PixelInterface[][] getPixelGrid() {
    return this.grid.clone();
  }
}
