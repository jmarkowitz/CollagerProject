package gui;

import model.ProjectModel;

public class Controller implements Features {

  private ProjectModel model;
  private GUIProjectView view;

  public Controller(ProjectModel model, GUIProjectView view) {
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void newProject(int height, int width) {
    try {
      this.model.newProject(height, width);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage(e.getMessage() + System.lineSeparator());
    }
  }

  @Override
  public void loadProject() {

  }

  @Override
  public void saveProject() {

  }

  @Override
  public void saveImageAs() {

  }

  @Override
  public void quit() {

  }

  @Override
  public void addLayer() {

  }

  @Override
  public void addImageToLayer() {

  }

  @Override
  public void setFilter() {

  }
}
