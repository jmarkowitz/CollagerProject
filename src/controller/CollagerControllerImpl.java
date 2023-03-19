package controller;

import controller.commands.AddImage;
import controller.commands.AddLayer;
import controller.commands.LoadProject;
import controller.commands.NewProject;
import controller.commands.SaveImage;
import controller.commands.SaveProject;
import controller.commands.SetFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.ProjectModel;
import view.ProjectView;

/**
 * Represents the controller for the Image Collage program. Allows the user to control the program
 * by giving commands.
 */
public class CollagerControllerImpl implements CollagerController {

  private final ProjectModel model;
  private final ProjectView view;
  private final Readable in;
  private boolean programStarted;
  private final Map<String, Function<Scanner, CollagerCommand>> knownCommands;

  /**
   * Constructs a CollagerControllerImpl object.
   *
   * @param model the model for the program
   * @param view  the view for the program
   * @param in    the input for the program
   */
  public CollagerControllerImpl(ProjectModel model, ProjectView view, Readable in) {
    if (model == null) {
      throw new IllegalArgumentException("Cannot give null model. Must be initialized.");
    } else if (in == null) {
      throw new IllegalArgumentException(
          "Cannot give null Readable input. Must be initialized first.");
    } else if (view == null) {
      throw new IllegalArgumentException("Cannot give null view. Must be initialized");
    }
    this.model = model;
    this.view = view;
    this.in = in;
    this.knownCommands = new HashMap<>();
  }

  private void initCommands() {
    this.knownCommands.put("new-project", (Scanner s) -> new NewProject(s, view));
    this.knownCommands.put("load-project", (Scanner s) -> new LoadProject(s, view));
    this.knownCommands.put("save-project", (Scanner s) -> new SaveProject(s, view));
    this.knownCommands.put("add-layer", (Scanner s) -> new AddLayer(s, view));
    this.knownCommands.put("add-image-to-layer", (Scanner s) -> new AddImage(s, view));
    this.knownCommands.put("set-filter", (Scanner s) -> new SetFilter(s, view));
    this.knownCommands.put("save-image", (Scanner s) -> new SaveImage(s, view));
  }

  /**
   * Starts the Image Collage program.
   *
   * @throws IllegalStateException if the controller cannot successfully read input or output
   */
  @Override
  public void startProgram() throws IllegalStateException {
    try {
      Scanner programScanner = new Scanner(this.in);
      this.initCommands();
      while (programScanner.hasNext()) {
        CollagerCommand c;
        String in = programScanner.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
          return;
        }
        Function<Scanner, CollagerCommand> cmd = knownCommands.getOrDefault(in, null);
        if (cmd == null) {
          view.renderMessage("Invalid command" + System.lineSeparator());
        } else {
          c = cmd.apply(programScanner);
          try {
            c.execute(model);
          } catch (IOException | InputMismatchException e) {
            view.renderMessage("Try again:" + System.lineSeparator());
          }
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Something went wrong when controlling");
    }

  }
}
