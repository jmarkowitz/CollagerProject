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

  private final String layerName;

  private final String filterName;

  /**
   * Constructs a set filter command.
   * @param layerName the name of the layer to apply the filter to
   * @param filterName the name of the filter to apply
   */
  public SetFilter(String layerName, String filterName) {
    this.layerName = layerName;
    this.filterName = filterName;
  }


  /**
   * Method that will execute the command.
   */
  @Override
  public void execute(ProjectModel model, ProjectView view) throws IOException {
      try {
        model.setFilter(layerName, filterName);
        view.renderMessage(filterName + " filter was successfully added to " + layerName + " layer"
            + System.lineSeparator());
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException();
      }
    }
  }
