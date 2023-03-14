package model;

public interface ImageModelInterface {

  void newProject(int canvas_height, int canvas_width);

  void loadProject(String path_to_project_file);

  void saveProject();

  void addLayer(String layer_name);

  void addImageToLayer(String layer_name, String image_name, int x_pos, int y_pos);

  void getImageName();

  void getLayerName();

  void getXPos();

  void getYPos();

  void getR();
  void getG();
  void getB();

}
