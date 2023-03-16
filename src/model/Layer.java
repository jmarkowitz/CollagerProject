package model;

public class Layer implements LayerInterface {

  private final String layerName;
  private String filterName;
  private PixelInterface[][] grid;

  public Layer(String layerName) {
    this.layerName = layerName;
    this.filterName = "Normal";
  }

  public Layer(String layerName, PixelInterface[][] grid) {
    this.layerName = layerName;
    this.filterName = "Normal";
    this.grid = grid;
  }

  @Override
  public String getName() {
    return this.layerName;
  }

  @Override
  public String getFilterName() {
    return this.filterName;
  }

  @Override
  public void setFilter(String filterOption) {
    this.filterName = filterOption;
  }

  @Override
  public PixelInterface[][] getPixelGrid() {
    return this.grid.clone();
  }
}
