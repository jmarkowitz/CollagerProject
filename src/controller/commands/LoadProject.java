package controller.commands;

import controller.CollagerCommand;
import controller.FileUtil;
import java.util.Scanner;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;
import model.ProjectModel;

public class LoadProject implements CollagerCommand {

  private final Scanner scanner;

  public LoadProject(Scanner scanner) {
    this.scanner = scanner;
  }


  @Override
  public void execute(ProjectModel model) {
    while (this.scanner.hasNext()) {
      String projectFilepath = this.scanner.next();
      String projectString = FileUtil.readFileAsString(projectFilepath);
//      String[] projectSplitByNewLine = projectString.split("\n");
//      //TODO: should we check here to see if this is a valid project file?
//      String[] widthAndHeight = projectSplitByNewLine[1].split(" ");
//      int width = Integer.parseInt(widthAndHeight[0]);
//      int height = Integer.parseInt(widthAndHeight[1]);
//      int maxValue = Integer.parseInt(projectSplitByNewLine[2]);
//      String[] allLayers = projectSplitByNewLine[3]
//      String[] layerNameFilterName = projectSplitByNewLine[3].split(" ");
//      for ()
      int width;
      int height;

      Scanner sc;
      sc = new Scanner(projectString);
      String token;
      token = sc.next();
      if (!token.equals("C1")) {
        throw new IllegalArgumentException("Invalid Project file: plain RAW file should begin with C1");
      }
      width = sc.nextInt();
      height = sc.nextInt();
      model.newProject(height, width);
      int maxValue = sc.nextInt();
      while (sc.hasNext()) {
        for (int row = 0; row < height; row++) {
          for (int col = 0; col < width; col++) {
            int red = sc.nextInt();
            int green = sc.nextInt();
            int blue = sc.nextInt();
            int alpha = sc.nextInt();
          }
        }
      }
      for (LayerInterface layer : layers) {
        model.addLayer(layerName);
        model.setFilter(layerName, filterName);
      }
    }
  }

  private
}
