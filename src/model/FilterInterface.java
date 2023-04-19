package model;

import java.util.function.BiFunction;

/**
 * Represents a filter that can be used on a layer. Allows the user to apply a filter on a 2D array
 * of pixels and get the filtered image back.
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
