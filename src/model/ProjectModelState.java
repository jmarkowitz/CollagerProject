package model;

import java.util.Map;

/**
 * Interface that represents the running state of a Collager project. This interface allows for
 * observation of the model's width, height, an ordered map of layers, and a map of all the filters.
 * This does not allow for any mutation of the width, height, layers, or filters.
 */
public interface ProjectModelState {

  /**
   * Returns the width as an integer in pixel units representing the width of the canvas.
   *
   * @return the width of the pixel grid
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  int getWidth() throws IllegalStateException;

  /**
   * Returns the height as an integer in pixel units representing the height of the canvas.
   *
   * @return the height of the pixel grid
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  int getHeight() throws IllegalStateException;

  /**
   * Returns a copy of all the layers currently in the canvas.
   *
   * @return all the layers in the canvas
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  Map<String, LayerInterface> getLayers() throws IllegalStateException;

  /**
   * Returns a copy of all filters available in the project.
   *
   * @return all the filters in the canvas
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  Map<String, FilterInterface> getAllFilters() throws IllegalStateException;

  /**
   * Returns a string representation of the project.
   * @return the string representation of the project
   * @throws IllegalStateException if this method is called before a project is created or loaded in
   */
  String exportProject() throws IllegalStateException;

  /**
   * Compresses all the layers into one 2D array of {@code Pixel}s.
   * @return the 2D array of {@code Pixel}s
   * @throws IllegalStateException if this method is called before a project is created or loaded in
   */
  PixelInterface[][] compressLayers() throws IllegalStateException;

}
