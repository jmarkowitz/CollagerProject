package model;

public class Layer {

  private String name;
  private int height;
  private int width;

  private String filterName;

  private Pixel[][] layer;

  //TODO: What should a layer consist of?
  // Can a layer have multiple filters? up to us but not required
  // Can a layer have multiple images? no
  // How should we represent a stack of layers?
  // Do we need to keep track of all filters, or when a new filter is added, any others prior are wiped?

}
