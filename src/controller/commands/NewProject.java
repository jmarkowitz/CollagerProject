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

  public NewProject(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) throws IOException {
    while (this.scanner.hasNext()) {
      int height = this.scanner.nextInt();
      int width = this.scanner.nextInt();
      try {
        model.newProject(height, width);
        view.renderMessage("New Project created" + System.lineSeparator());
        break;
      } catch (IllegalArgumentException e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException();
      }
    }

  }
}
