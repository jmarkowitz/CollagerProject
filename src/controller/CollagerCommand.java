package controller;

import java.io.IOException;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents how certain commands can be executed. Any command subclass that implements this will
 * be able to change the model in some way based on the command chosen.
 */
public interface CollagerCommand {

  /**
   * Method that will execute the command of any subclasses that implement it.
   *
   * @param model the model to be modified
   * @param view  the view to update
   * @throws IOException if there is an error executing the command
   */
  void execute(ProjectModel model, ProjectView view) throws IOException;

}
