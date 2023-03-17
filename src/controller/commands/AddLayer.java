package controller.commands;

import controller.CollagerCommand;
import java.util.Scanner;
import model.ProjectModel;

/**
 * Represents a command to add a layer to an existing project
 */
public class AddLayer implements CollagerCommand {

  private final Scanner scanner;

  public AddLayer(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      String layerName = this.scanner.next();
      model.addLayer(layerName);
    }

  }
}
