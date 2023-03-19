package controller.commands;

import controller.CollagerCommand;
import java.io.IOException;
import java.util.Scanner;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents a command that will create a new Collager project
 */
public class NewProject implements CollagerCommand {

  private final Scanner scanner;
  private final ProjectView view;

  /**
   * Constructor for the new project command.
   *
   * @param scanner the scanner that will be used to read the user input
   * @param view    the view that will be used to display the output
   */
  public NewProject(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }

  /**
   * Method that will execute the new project command.
   */
  @Override
  public void execute(ProjectModel model) throws IOException {
    while (this.scanner.hasNext()) {
      int height = this.scanner.nextInt();
      int width = this.scanner.nextInt();
      try {
        model.newProject(height, width);
        view.renderMessage(
            "New Project created with dimensions " + height + "x" + width + System.lineSeparator());
        break;
      } catch (IllegalArgumentException e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException();
      }
    }

  }
}
