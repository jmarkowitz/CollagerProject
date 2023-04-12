package controller.commands;

import controller.CollagerCommand;
import controller.file.FileHandler;
import controller.file.TextProjectHandler;
import java.io.IOException;
import model.ProjectModel;
import view.ProjectView;

/**
 * Class that represents the load project command.
 */
public class LoadProject implements CollagerCommand {

  private final String filepath;

  /**
   * Constructs a load project command.
   *
   * @param filepath the filepath to load the project from
   */
  public LoadProject(String filepath) {
    this.filepath = filepath;
  }

  /**
   * Method that will execute the load project command.
   *
   * @param model the model that will be used to load the project
   * @param view  the view that will be used to render the message
   * @throws IOException              if there is an issue rendering the message
   * @throws IllegalArgumentException if the file is invalid
   */
  @Override
  public void execute(ProjectModel model, ProjectView view)
      throws IOException, IllegalArgumentException {
    FileHandler<String> fileReader = new TextProjectHandler(model);
    String projectString = null;
    try {
      projectString = fileReader.readFile(filepath);
    } catch (IOException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
    }
    try {
      model.buildProject(projectString);
      view.renderMessage("Project loaded successfully" + System.lineSeparator());
    } catch (IllegalStateException | IllegalArgumentException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
    }
  }

}
