package model.project;

/**
 * Represents functionality for dealing with Collage projects. This will allow projects to be
 * created, loaded and saved. Any metadata related to the project will be saved and formatted based
 * on the Project file type.
 */
public interface Project {

  /**
   * Allows for creation of a new file that will create a model for any metadata to be saved.
   */
  void newProject();

  /**
   * Will load a project from a String filepath provided.
   */
  void loadProject(String filepath);

  /**
   * Will save a project to a particular file based on the type chosen
   */
  void saveProject();

}
