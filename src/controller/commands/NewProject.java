package controller.commands;

import controller.CollagerCommand;
import java.util.Scanner;
import model.ProjectModel;

/**
 * Represents a command that will create a new Collager project
 */
public class NewProject implements CollagerCommand {

  private final Scanner scanner;

  public NewProject(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      int height = this.scanner.nextInt();
      int width = this.scanner.nextInt();
      model.newProject(height, width);
    }

  }
}
