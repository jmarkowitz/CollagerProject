package controller.file;

import java.io.FileWriter;
import java.io.IOException;
import model.ProjectModel;

/**
 * Represents a class that allows the user to read and write a representation of a project to a
 * .collage or text file.
 */
public class TextProjectHandler extends AbstractFileHandler {

  /**
   * Constructs a TextProjectHandler object.
   *
   * @param model the model for the program
   */
  public TextProjectHandler(ProjectModel model) {
    super(model);
  }

  /**
   * This method get all the information in a file.
   *
   * @param filepath The filepath of a file.
   * @return All the information in a file.
   * @throws IOException if the file is invalid.
   */
  @Override
  public String readFile(String filepath) throws IOException {
    return FileUtil.readFileAsString(filepath);
  }

  /**
   * This method read the information to a file.
   *
   * @param filepath the filepath of a file.
   * @throws IOException if the file is invalid.
   */
  @Override
  public void writeFile(String filepath) throws IOException {
    FileWriter fileWriter;
    fileWriter = new FileWriter(filepath);
    fileWriter.write(this.modelState.exportProject());
    fileWriter.close();
  }
}
