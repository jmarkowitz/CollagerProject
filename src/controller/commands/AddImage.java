package controller.commands;

import controller.CollagerCommand;
import controller.file.FileHandler;
import controller.file.FileUtil;
import controller.file.ImageHandler;
import controller.file.PPMHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.PixelInterface;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents the command that will add an image to a layer.
 */
public class AddImage implements CollagerCommand {

  private final String layerName;
  private final String imagePath;
  private final int x;
  private final int y;

  /**
   * Constructs an AddImage command.
   *
   * @param layerName the name of the layer to add the image to
   * @param imagePath the path of the image to add
   * @param x         the x coordinate of the image
   * @param y         the y coordinate of the image
   */
  public AddImage(String layerName, String imagePath, int x, int y) {
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
    FileHandler<PixelInterface[][]> imageHandler;
    String fileExt = FileUtil.getFileExtension(imagePath);
    switch (fileExt) {
      case "ppm":
        imageHandler = new PPMHandler(model);
        break;
      case "jpg":
      case "jpeg":
      case "png":
        imageHandler = new ImageHandler(model);
        break;
      default:
        view.renderMessage("Invalid file extension" + System.lineSeparator());
        throw new IOException("Invalid file extension" + System.lineSeparator());
    }
    PixelInterface[][] image = null;
    try {
      image = imageHandler.readFile(imagePath);
      view.renderMessage("Image loaded successfully" + System.lineSeparator());
    } catch (FileNotFoundException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
    }
    try {
      model.addImageToLayer(layerName, image, x, y);
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage() + System.lineSeparator());
    }
  }
}
