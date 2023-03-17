package controller.commands;

import controller.CollagerCommand;
import java.util.Scanner;
import model.ProjectModel;

public class LoadProject implements CollagerCommand {

  private final Scanner scanner;

  public LoadProject(Scanner scanner) {
    this.scanner = scanner;
  }


  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      String projectFilepath = this.scanner.next();
      //TODO: some file reading stuff happens...
      //model.newProject(height, width);
      //model.addLayer();

    }

  }
}
