package controller.commands;


import controller.CollagerCommand;
import controller.file.FileHandler;
import controller.file.FileUtil;
import controller.file.ImageHandler;
import controller.file.PPMHandler;
import java.io.IOException;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents a command that will allow the user to save an image to the type they want to by using
 * the {@code ImageHandler} and {@code PPMHandler} implementations of the {@code FileHandler}
 * interface.
 */
public class SaveImage implements CollagerCommand {

  private final String imagePath;

  /**
   * Constructs a save image command.
   *
   * @param imagePath the path to save the image to
   */
  public SaveImage(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Method that will execute the command.
   */
  @Override
  public void execute(ProjectModel model, ProjectView view) throws IOException {
    FileHandler imageHandler;
    String fileExt = FileUtil.getFileExtension(imagePath);
    switch (fileExt) {
      case "ppm":
        imageHandler = new PPMHandler(model);
        break;
      case "png":
      case "jpeg":
      case "jpg":
        imageHandler = new ImageHandler(model);
        break;
      default:
        view.renderMessage("Invalid file extension");
        throw new IOException("Invalid file extension");
    }
    try {
      imageHandler.writeFile(imagePath);
      view.renderMessage("Image saved successfully" + System.lineSeparator());
    } catch (IOException e) {
      view.renderMessage(e.getMessage());
    }
  }
}

