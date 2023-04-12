package model;

import java.util.function.BiFunction;

/**
 * Represents a filter that can be used on a layer. Allows the user to apply a filter, get the name,
 * and return a valid value.
 */
@FunctionalInterface
public interface FilterInterface extends
    BiFunction<PixelInterface[][], PixelInterface[][], PixelInterface[][]> {

  /**
   * Applies the filter to the given layer and returns the layer with the filtered pixels.
   *
   * @param curImage the layer to be filtered
   * @param bgImage  the background image for certain filters
   * @return the filtered layer
   */
  @Override
  PixelInterface[][] apply(PixelInterface[][] curImage, PixelInterface[][] bgImage);
}
