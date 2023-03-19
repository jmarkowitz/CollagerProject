package view;

import java.io.IOException;

/**
 * Represents a command line text view of a collager project.
 */
public class CommandLineTextView implements ProjectView {
  private final Appendable viewOut;

  public CommandLineTextView() {
    this.viewOut = System.out;
  }

  public CommandLineTextView(Appendable viewOut) {
    this.viewOut = viewOut;
  }

  /**
   * Renders a given message to the data output in the implementation.
   *
   * @param message the message to be printed
   * @throws IOException if the transmission of the message to the data output fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    this.viewOut.append(message);
  }
}
