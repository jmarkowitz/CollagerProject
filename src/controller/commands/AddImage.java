package controller.commands;

import controller.CollagerCommand;
import controller.FileUtil;
import java.io.IOException;
import java.util.Scanner;
import model.Image;
import model.ProjectModel;
import view.ProjectView;

public class AddImage implements CollagerCommand {

  private final Scanner scanner;
  private final ProjectView view;

  public AddImage(Scanner scanner, ProjectView view) {
    this.scanner = scanner;
    this.view = view;
  }

  /**
   * Method that will execute the command
   */
  @Override
  public void execute(ProjectModel model) throws IOException {
    while (this.scanner.hasNext()) {
      String layerName = this.scanner.next();
      String imagePath = this.scanner.next();
      int x = this.scanner.nextInt();
      int y = this.scanner.nextInt();
      String imageString = null;
      try {
        imageString = FileUtil.readFileAsString(imagePath);
      } catch (Exception e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException();
      }

      try {
        model.addImageToLayer(layerName, Image.readPPM(imageString, model.getHeight(),
            model.getWidth()), x, y);
        view.renderMessage(imagePath + " added successfully" + System.lineSeparator());
        break;
      } catch (IllegalArgumentException | IllegalStateException e) {
        this.view.renderMessage(e.getMessage() + System.lineSeparator());
        throw new IOException();
      }
    }
  }
}
