import controller.CollagerControllerImpl;
import java.io.InputStreamReader;
import model.ImageUtil;
import model.Project;
import model.ProjectModel;
import view.CommandLineTextView;
import view.ProjectView;

public class Main {
  public static void main(String[] args) {
    ProjectModel projectModel = null;
    for (String arg : args) {
      switch (arg) {
        case "start":
          projectModel = new Project();
          break;
        default:
          throw new IllegalArgumentException("Invalid program argument passed into main");
      }
    }
    ProjectView projectView = new CommandLineTextView();
    new CollagerControllerImpl(projectModel, projectView,
        new InputStreamReader(System.in)).startProgram();
  }
}
