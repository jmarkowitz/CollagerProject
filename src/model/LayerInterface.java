package model;

/**
 * Represents a collection of pixels that has a name and a filter associated with it.
 */
public interface LayerInterface {

  /**
   * Returns the name of the layer.
   *
   * @return the name of the layer
   */
  String getName();

  /**
   * Returns the name of the filter associated with the layer.
   *
   * @return the name of the filter
   */
  String getFilter();

  /**
   * Sets the name of the layer.
   *
   * @param filterOption the name of the layer
   */
  void setFilter(String filterOption);

  /**
   * Returns a copy of the 2D array of pixels that make up the layer.
   *
   * @return a 2D array of pixels
   */
  PixelInterface[][] getPixelGrid();

}
