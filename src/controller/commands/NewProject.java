package controller.commands;

import controller.CollagerCommand;
import java.io.IOException;
import java.util.Scanner;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents a command that will create a new Collager project.
 */
public class NewProject implements CollagerCommand {

  private final int height;
  private final int width;

  /**
   * Constructs a new project command
   *
   * @param height the height of the project to be created
   * @param width  the width of the project to be created
   */
  public NewProject(int height, int width) {
    this.height = height;
    this.width = width;
  }

  /**
   * Method that will execute the new project command.
   */
  @Override
  public void execute(ProjectModel model, ProjectView view) throws IOException {
    try {
      model.newProject(height, width);
      view.renderMessage(
          "New Project created with dimensions " + height + "x" + width + System.lineSeparator());
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
      throw new IOException();
    }
  }
}
