package model;

public interface LayerInterface {
  String getName();
  String getFilter();
  void setFilter(String filterOption);
  PixelInterface[][] getPixelGrid();

}
