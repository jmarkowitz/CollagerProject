package controller.commands;

import controller.CollagerCommand;
import controller.file.FileHandler;
import controller.file.FileUtil;
import controller.file.ImageHandler;
import controller.file.PPMHandler;
import controller.file.TextProjectHandler;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import model.LayerInterface;
import model.PixelInterface;
import model.ProjectModel;
import view.ProjectView;

/**
 * Saves and exports all the data in the model to a file location.
 */
public class SaveProject implements CollagerCommand {

  private final String filepath;

  /**
   * Constructs a save project command.
   *
   * @param filepath the path to save the project to
   */
  public SaveProject(String filepath) {
    this.filepath = filepath;
  }

  /**
   * Saves and exports all the data in the model to a file location that includes the following
   * below.
   * <ul>
   * <li> the project name</li>
   * <li> the width and height</li>
   * <li> the maximum value for one pixel</li>
   * <li> all layers' name, their respective filter and the UNMODIFIED RGBA pixels in each layer
   * separated by a new line </li>
   * </ul>
   *
   * @throws IOException if this method is called before a project has been created or
   *                               loaded
   */
  @Override
  public void execute(ProjectModel model, ProjectView view) throws IOException {
    FileHandler projectHandler = new TextProjectHandler(model);
    try {
      projectHandler.writeFile(filepath);
    } catch (IOException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
    }
  }
}
