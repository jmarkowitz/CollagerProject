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

  private final String layerName;

  /**
   * Constructs an add layer command
   *
   * @param layerName the name of the layer to add
   */
  public AddLayer(String layerName) {
    this.layerName = layerName;
  }

  /**
   * Executes the command to add a layer to the project.
   */
  @Override
  public void execute(ProjectModel model, ProjectView view) throws IOException {
    try {
      model.addLayer(layerName);
      view.renderMessage("New layer " + layerName + " added to project" + System.lineSeparator());
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
      throw new IOException();
    }
  }
}
