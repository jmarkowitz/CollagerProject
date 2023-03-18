package model;

public class Layer implements LayerInterface {

  private final String layerName;
  private String filter;
  private PixelInterface[][] grid;

  public Layer(String layerName) {
    this.layerName = layerName;
    this.filter = "normal";
  }

  public Layer(String layerName, PixelInterface[][] grid) {
    this.layerName = layerName;
    this.filter = "normal";
    this.grid = grid;
  }

  @Override
  public String getName() {
    return this.layerName;
  }

  @Override
  public String getFilter() {
    return this.filter;
  }

  @Override
  public void setFilter(String filterOption) {
    this.filter = filterOption;
  }

  @Override
  public PixelInterface[][] getPixelGrid() {
    return this.grid.clone();
  }
}
