package controller.file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import model.LayerInterface;
import model.PixelInterface;
import model.ProjectModelState;

public class TextProjectHandler extends AbstractFileHandler {

  public TextProjectHandler(ProjectModelState modelState) {
    super(modelState);//TODO: Should this be model?
  }

  @Override
  public Object readFile(String filepath) throws IOException {
    return null; //TODO: should this take in the model and called newProject, etc?
  }

  @Override
  public void writeFile(String filepath) throws IOException {
    FileWriter fileWriter;
      fileWriter = new FileWriter(filepath);
      fileWriter.write(this.formatExport(projectWidth, projectHeight, layerMap));
      fileWriter.close();
  }

  private String formatExport(int width, int height, Map<String, LayerInterface> projectLayers) {
    StringBuilder finalString = new StringBuilder();
    finalString.append("C1").append(System.lineSeparator());
    finalString.append(width).append(" ").append(height).append(System.lineSeparator());
    finalString.append("255").append(System.lineSeparator());
    for (Map.Entry<String, LayerInterface> mapLayer : projectLayers.entrySet()) {
      LayerInterface currentLayer = mapLayer.getValue();
      String currentLayerName = mapLayer.getKey();
      finalString.append(currentLayerName).append(" ").append(currentLayer.getFilter())
          .append(System.lineSeparator());
      PixelInterface[][] curLayerPixels = currentLayer.getPixelGrid();
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          finalString.append(curLayerPixels[row][col].toString(0)).append(System.lineSeparator());
        }
      }
    }
    return finalString.toString();
  }
}
