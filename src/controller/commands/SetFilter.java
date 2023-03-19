package controller.commands;

import controller.CollagerCommand;
import java.io.IOException;
import java.util.Scanner;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents a command that will set a filter to a layer.
 */
public class SetFilter implements CollagerCommand {

  private final Scanner scanner;

  private final ProjectView view;

  /**
   * Constructor for the SetFilter command.
   *
   * @param scanner the scanner to read the layer name and filter name from
   * @param view    the view to render the message to
   */
  public SetFilter(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }

  /**
   * Method that will execute the command.
   */
  @Override
  public void execute(ProjectModel model) throws IOException {
    while (this.scanner.hasNext()) {
      String layer = this.scanner.next();
      String filterName = this.scanner.next();
      try {
        model.setFilter(layer, filterName);
        this.view.renderMessage(filterName + " filter was successfully added to " + layer + " layer"
            + System.lineSeparator());
        break;
      } catch (IllegalArgumentException | IllegalStateException e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException();
      }
    }
  }
}
