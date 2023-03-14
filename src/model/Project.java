package model;

import java.util.Stack;

public class Project implements ProjectModel {
  public static final int MAX_VALUE = 255;

  private String projectName;
  private int height;
  private int width;
  private Stack<Layer> layerStack; //TODO: Would this be a good way to represent this so we can undo?

}
