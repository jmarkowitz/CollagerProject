package model;

public interface LayerInterface {

  int getHeight();
  int getWidth();
  String getName();
  void setFilter(String filterOption);
  PixelInterface[][] getPixelGrid();

}
