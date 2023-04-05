package gui.view;

import gui.controller.Features;
import java.util.List;
import model.PixelInterface;

/**
 * Represents the view for the GUI. This interface allows for the controller to interact with the
 * view, and update any implementation to allow the user to visibly see how the program will change
 * according to their input.
 */
public interface GUIProjectView {

  /**
   * Adds the given features of the controller to the view. This allows the view to interact with
   * the controller and tell it when the user has done something related to an action listener.
   *
   * @param features the features of the controller
   */
  void addFeatures(Features features);

  /**
   * Adds a new layer to the view's layer list with the given layer name.
   *
   * @param layerName the name of the layer
   */
  void addLayer(String layerName);

  /**
   * Adds the given filters to the view's filter list so the user can see all available filters.
   *
   * @param filters the list of filter names
   */
  void addFilters(List<String> filters);

  /**
   * Takes in a 2D array of pixels and turns it into a {@code BufferedImage} that the view with then
   * render to the user. This method will replace the existing image with the updated image if
   * called more than once.
   *
   * @param image the 2D array of pixels
   */
  void renderImage(PixelInterface[][] image);

  /**
   * Given the String passed in, it will render a popup window with the message to the user.
   *
   * @param message the message to be displayed
   */
  void renderMessage(String message);

  /**
   * Activates all the buttons in the view once a project has been loaded in or created. This
   * prevents the user trying to add layers, filter layers, or adding images to layers without the
   * program being started.
   */
  void activateButtons();

  /**
   * Refreshes the entire view and repaints all the components.
   */
  void refresh();

}
