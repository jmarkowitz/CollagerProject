package model;

import java.util.function.Function;

/**
 * Represents a filter that can be used on a layer. Allows the user to apply a filter, get the name, and return a valid value.
 */
public interface FilterInterface extends Function<LayerInterface, LayerInterface> {

  /**
   * Applies the filter to the given layer and returns the layer with the filtered pixels.
   *
   * @param layer the layer to be filtered
   * @return the filtered layer
   */
  @Override
  LayerInterface apply(LayerInterface layer);

  /**
   * Returns the name of the filter.
   *
   * @return the new name of the filter
   */
  String getFilterName();
}
