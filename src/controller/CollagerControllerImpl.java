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
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.ProjectModel;
import view.ProjectView;

public class CollagerControllerImpl implements CollagerController {

  private final ProjectModel model;
  private final ProjectView view;
  private final Readable in;
  private boolean programStarted; //TODO: need to implement this so new project

  Map<String, Function<Scanner, CollagerCommand>> knownCommands;

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
    this.knownCommands.put("new-project", (Scanner s) -> new NewProject(s));
    this.knownCommands.put("load-project", (Scanner s) -> new LoadProject(s));
    this.knownCommands.put("save-project", (Scanner s) ->  new SaveProject(s));
    this.knownCommands.put("add-layer", (Scanner s) ->  new AddLayer(s));
    this.knownCommands.put("add-image-to-layer", (Scanner s) ->  new AddImage(s));
    this.knownCommands.put("set-filter", (Scanner s) ->  new SetFilter(s));
    this.knownCommands.put("save-image", (Scanner s) ->  new SaveImage(s));
  }

  /**
   * Starts the Image Collage program.
   * @throws IllegalStateException if the controller cannot successfully read input or output
   */
  @Override
  public void startProgram() throws IllegalStateException {
    try {
      Scanner programScanner = new Scanner(this.in);
      this.initCommands();
      while(programScanner.hasNext()) {
        CollagerCommand c;
        String in = programScanner.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit"))
          return;
        Function<Scanner, CollagerCommand> cmd = knownCommands.getOrDefault(in, null);
        if (cmd == null) {
          view.renderMessage("Invalid command" + System.lineSeparator());
        } else {
          c = cmd.apply(programScanner);
          //commands.add(c);
          try {
            c.execute(model);
          } catch (Exception e) {
            view.renderMessage(e.getMessage() + System.lineSeparator());
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
