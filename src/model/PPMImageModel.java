package model;

public class PPMImageModel implements ImageModelInterface{
  final protected int R;

  final protected int G;

  final protected int B;


  final protected int X_Pos;

  final protected int Y_Pos;

  public PPMImageModel(int r, int g, int b, int xPos, int yPos) {
    R = r;
    G = g;
    B = b;
    X_Pos = xPos;
    Y_Pos = yPos;
  }


  @Override
  public void newProject(int canvas_height, int canvas_width) {

  }

  @Override
  public void loadProject(String path_to_project_file) {

  }

  @Override
  public void saveProject() {

  }

  @Override
  public void addLayer(String layer_name) {

  }

  @Override
  public void addImageToLayer(String layer_name, String image_name, int x_pos, int y_pos) {

  }

  @Override
  public void getImageName() {

  }

  @Override
  public void getLayerName() {

  }

  @Override
  public void getXPos() {

  }

  @Override
  public void getYPos() {

  }

  @Override
  public void getR() {

  }

  @Override
  public void getG() {

  }

  @Override
  public void getB() {

  }

}
