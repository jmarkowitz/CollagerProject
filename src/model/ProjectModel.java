package model;

public interface ProjectModel<P> extends ProjectModelState<P> {

  void newProject(int width, int height); //TODO: how do we set the max number
  void addLayer();
  void setFilter(String layerName, String filterOption);
  void addImageToLayer(String layerName, String imagePath, int x, int y);
  void saveImage(String filepath);
  void loadProject(String filepath);
  void saveProject();

}
