package controller.commands;

import controller.CollagerCommand;
import java.io.IOException;
import java.util.Scanner;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents a command to add a layer to an existing project.
 */
public class AddLayer implements CollagerCommand {

  private final Scanner scanner;
  private final ProjectView view;

  /**
   * Constructor for the AddLayer command.
   *
   * @param scanner the scanner to read the layer name from
   * @param view    the view to render the message to
   */
  public AddLayer(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }

  /**
   * Executes the command to add a layer to the project.
   */
  @Override
  public void execute(ProjectModel model) throws IOException {
    while (this.scanner.hasNext()) {
      String layerName = this.scanner.next();
      try {
        model.addLayer(layerName);
        view.renderMessage("New layer " + layerName + " added to project" + System.lineSeparator());
        break;
      } catch (IllegalArgumentException | IllegalStateException e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException();
      }
    }

  }
}
