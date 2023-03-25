package controller.file;

import model.ProjectModel;
import model.ProjectModelState;

public abstract class AbstractFileHandler<F> implements FileHandler<F> {

  private enum FileType {
    TXT("txt"),
    COLLAGE("collage"),
    JSON("json"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    PPM("ppm");

    private final String extension;

    FileType(String extension) {
      this.extension = extension;
    }

    public String getExtension() {
      return this.extension;
    }
  }

  protected final ProjectModelState modelState;

  public AbstractFileHandler(ProjectModelState modelState) {
    this.modelState = modelState;
  }

  protected String getFileExtension(String filepath) {
    int dotIndex = filepath.lastIndexOf('.');
    if (dotIndex == -1 || dotIndex == filepath.length() - 1) {
      return "";
    } else {
      return filepath.substring(dotIndex + 1).toLowerCase();
    }
  }

  protected int scalePixel(int pixelValue, int maxValue) {
    return (int) Math.round(pixelValue * (255.0 / maxValue));
  }

}
