package controller;

/**
 * Represents the controller for a {@code ProjectModel}. Controls the model and updates the view
 * accordingly.
 */
public interface CollagerController {

  /**
   * Starts the Image Collage program.
   *
   * @throws IllegalStateException if the method is called before the model and view have been set.
   */
  void startProgram() throws IllegalStateException;

}
