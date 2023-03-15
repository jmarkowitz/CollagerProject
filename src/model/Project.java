package model;

import java.util.Stack;

public class Project {//TODO: make this implement ProjectModel<P>
  public static final int MAX_VALUE = 255;//TODO: probably get rid of this

  private String projectName;
  private int height;
  private int width;
  private Stack<Layer> layerStack; //TODO: Would this be a good way to represent this so we can undo?

}
