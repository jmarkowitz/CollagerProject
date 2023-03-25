package controller.file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModel;

public class TextProjectHandler extends AbstractFileHandler {

  public TextProjectHandler(ProjectModel model) {
    super(model);
  }

  @Override
  public String readFile(String filepath) throws IOException {
    return FileUtil.readFileAsString(filepath);
  }

  @Override
  public void writeFile(String filepath) throws IOException {
    FileWriter fileWriter;
      fileWriter = new FileWriter(filepath);
      fileWriter.write(this.modelState.exportProject());
      fileWriter.close();
  }
}
