package controller;

import controller.commands.NewProject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.ProjectModel;

public class CollagerControllerImpl implements CollagerController {

  private final ProjectModel model;
  private final Readable in;
  private boolean programStarted;

  Map<String, Function<Scanner, CollagerCommand>> knownCommands;

  public CollagerControllerImpl(ProjectModel model, Readable in) {
    if (model == null) {
      throw new IllegalArgumentException("Cannot give null model. Must be initialized.");
    } else if (in == null) {
      throw new IllegalArgumentException(
          "Cannot give null Readable input. Must be initialized first.");
    }
    this.model = model;
    this.in = in;
    this.knownCommands = new HashMap<>();
  }

  private void initCommands() {
    this.knownCommands.put("new-project", (Scanner s) -> {return new NewProject();});
  }

  /**
   * Starts the Image Collage program.
   * @throws IllegalStateException if the controller cannot successfully read input or output
   */
  @Override
  public void startProgram() throws IllegalStateException {
    Scanner programScanner = new Scanner(this.in);
    this.initCommands();
    try {
      // something
    } catch (IOException e) {
      throw new IllegalStateException("Error transmitting output");
    }

  }
}
