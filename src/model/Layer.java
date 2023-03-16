package model;

public class Layer implements LayerInterface {

  private String layerName;
  private String filterName;
  private PixelInterface[][] grid;

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void setFilter(String filterOption) {

  }

  @Override
  public PixelInterface[][] getPixelGrid() {
    return new PixelInterface[0][];
  }
}
