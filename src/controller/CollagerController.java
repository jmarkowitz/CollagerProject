package controller;

/**
 * Represents the controller for a {@code ProjectModel}. Controls the model.
 */
public interface CollagerController {

  /**
   * Starts the Image Collage program.
   * @throws IllegalStateException if the controller cannot successfully read input or output
   */
  void startProgram() throws IllegalStateException;

}
