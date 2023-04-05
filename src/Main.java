import controller.CollagerControllerImpl;
import controller.file.FileUtil;
import gui.controller.FeatureController;
import gui.controller.Features;
import gui.view.GUIProjectView;
import gui.view.GUIProjectViewImpl;
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
    ProjectView projectView;
    Readable in;
    for (String arg : args) {
      switch (arg) {
        case "-text":
          in = new InputStreamReader(System.in);
          projectView = new CommandLineTextView();
          new CollagerControllerImpl(projectModel, projectView, in).startProgram();
          break;
        case "-file":
          try {
            in = new StringReader(FileUtil.readFileAsString(args[args.length - 1]));
            projectView = new CommandLineTextView();
            new CollagerControllerImpl(projectModel, projectView, in).startProgram();
          } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
          }
          break;
        default:
          throw new IllegalArgumentException("Invalid type");
      }
    }

    if (args.length == 0) {
      GUIProjectView view = new GUIProjectViewImpl("Collager");
      Features controller = new FeatureController(projectModel, view);
    }
  }
}
