package view;

import java.io.IOException;

/**
 * The interface for a client's perspective of the model of a collager project. Can be used to see
 * what the model looks like and how it can change.
 */
public interface ProjectView {

  /**
   * Renders a given message to the data output in the implementation.
   *
   * @param message the message to be printed
   * @throws IOException if the transmission of the message to the data output fails
   */
  void renderMessage(String message) throws IOException;
}
