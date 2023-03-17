package controller.commands;

import controller.CollagerCommand;
import java.io.IOException;
import java.util.Scanner;
import model.ImageUtil;
import model.PixelInterface;
import model.ProjectModel;

public class AddImage implements CollagerCommand {

  private final Scanner scanner;

  public AddImage(Scanner scanner) {
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
      PixelInterface[][] imageGrid = ImageUtil.readPPM(imagePath);
      model.addImageToLayer(layerName, imageGrid, x, y);
    }

  }
}
