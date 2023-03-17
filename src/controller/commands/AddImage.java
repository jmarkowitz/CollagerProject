package controller.commands;

import controller.CollagerCommand;
import controller.FileUtil;
import java.util.Scanner;
import model.Image;
import model.ProjectModel;

public class AddImage implements CollagerCommand {

  private final Scanner scanner;//TODO: should we be handling exceptions in each command? Or in controller

  public AddImage(Scanner scanner) { //TODO: could pass in the view so we can render the message
    this.scanner = scanner;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      String layerName = this.scanner.next();
      String imagePath = this.scanner.next();
      int x = this.scanner.nextInt();
      int y = this.scanner.nextInt();
      String imageString = FileUtil.readFileAsString(imagePath);

      model.addImageToLayer(layerName, Image.readPPM(imageString), x, y);
    }

  }
}
