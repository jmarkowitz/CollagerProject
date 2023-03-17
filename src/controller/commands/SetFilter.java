package controller.commands;

import controller.CollagerCommand;
import java.util.Scanner;
import model.ProjectModel;

public class SetFilter implements CollagerCommand {

  private final Scanner scanner;

  public SetFilter(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      String layer = this.scanner.next();
      String filterName = this.scanner.next();
      model.setFilter(layer, filterName);
    }
  }
}
