package controller.commands;

import controller.CollagerCommand;
import controller.file.FileUtil;
import java.io.IOException;
import model.Image;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents the command that will add an image to a layer.
 */
public class AddImageOld implements CollagerCommand {

  private final String layerName;
  private final String imagePath;
  private final int x;
  private final int y;

  public AddImageOld(String layerName, String imagePath, int x, int y) {
    this.layerName = layerName;
    this.imagePath = imagePath;
    this.x = x;
    this.y = y;
  }

  /**
   * Method that will execute the command.
   */
  @Override
  public void execute(ProjectModel model, ProjectView view) throws IOException {
    String imageString = null;
    try {
      imageString = FileUtil.readFileAsString(imagePath);
    } catch (Exception e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
      throw new IOException();
    }

    try {
      model.addImageToLayer(layerName, Image.readPPM(imageString, model.getHeight(),
          model.getWidth()), x, y);
      view.renderMessage(imagePath + " added successfully" + System.lineSeparator());
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
      throw new IOException();
    }
  }
}
