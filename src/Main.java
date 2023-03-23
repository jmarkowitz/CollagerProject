import controller.CollagerControllerImpl;
import controller.FileUtil;
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

    Readable in = null;
    for (String arg : args) {
      switch (arg) {
        case "cmdLine":
          in = new InputStreamReader(System.in);
          break;
        case "batch":
          in = new StringReader(FileUtil.readFileAsString("batch_command.txt"));
          break;
        default:
          throw new IllegalArgumentException("Invalid type");
      }
    }
    ProjectModel projectModel = new Project();
    ProjectView projectView = new CommandLineTextView();
    new CollagerControllerImpl(projectModel, projectView, in).startProgram();
  }
}
