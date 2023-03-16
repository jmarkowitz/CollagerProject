package model;

public interface LayerInterface {
  String getName();
  String getFilterName();
  void setFilter(String filterOption);
  PixelInterface[][] getPixelGrid();

}
