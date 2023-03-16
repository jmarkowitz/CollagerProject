package model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface that represents the running state of a Collager project. This interface allows for
 * observation of the model's width, height, and a list of layers. This does not allow for any
 * mutation of the width, height, and layers.
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
   * Returns a copy of the list of all the layers currently in the canvas
   *
   * @return the list of all layers in the canvas
   * @throws IllegalStateException if this method is called before a new project has been created or
   *                               loaded in
   */
  Map<String, LayerInterface> getLayers() throws IllegalStateException;

}
