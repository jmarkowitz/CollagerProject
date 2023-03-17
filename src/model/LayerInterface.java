package model;

public interface LayerInterface {
  String getName();
  FilterInterface getFilter();
  void setFilter(FilterInterface filterOption);
  PixelInterface[][] getPixelGrid();

}
