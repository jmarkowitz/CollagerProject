import controller.CollagerControllerImpl;
import controller.file.FileUtil;
import gui.FeatureController;
import gui.Features;
import gui.GUIProjectView;
import gui.GUIProjectViewImpl;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import model.Project;
import model.ProjectModel;
import view.CommandLineTextView;
import view.ProjectView;

/**
 * The main class for the Collager program.
 */
public class Main {

  /**
   * The main method for the Collager program.
   *
   * @param args the arguments passed into the program
   */
  public static void main(String[] args) {

    ProjectModel projectModel = new Project();
    ProjectView projectView = null;
    Readable in = null;
    for (String arg : args) {
      switch (arg) {
        case "cmdLine":
          in = new InputStreamReader(System.in);
          projectView = new CommandLineTextView();
          new CollagerControllerImpl(projectModel, projectView, in).startProgram();
          break;
        case "batch":
          try {
            in = new StringReader(FileUtil.readFileAsString(args[args.length - 1]));
            projectView = new CommandLineTextView();
            new CollagerControllerImpl(projectModel, projectView, in).startProgram();
          } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
          }
          break;
        case "gui":
          GUIProjectView view = new GUIProjectViewImpl("Collager");
          Features controller = new FeatureController(projectModel, view);
          break;
        default:
          throw new IllegalArgumentException("Invalid type");
      }
    }
  }
}
