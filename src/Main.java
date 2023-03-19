import controller.CollagerControllerImpl;
import java.io.InputStreamReader;
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
    ProjectModel projectModel = null;
    for (String arg : args) {
      if (arg.equals("start")) {
        projectModel = new Project();
      } else {
        throw new IllegalArgumentException("Invalid program argument passed into main");
      }
    }
    ProjectView projectView = new CommandLineTextView();
    new CollagerControllerImpl(projectModel, projectView,
        new InputStreamReader(System.in)).startProgram();
  }
}
