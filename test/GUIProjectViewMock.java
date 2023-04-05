import gui.controller.Features;
import gui.view.GUIProjectView;
import java.util.List;
import model.PixelInterface;

/**
 * Mock view class for testing the FeatureController.
 */
public class GUIProjectViewMock implements GUIProjectView {

  private final StringBuilder log;

  /**
   * Constructs a new mock view with the given log.
   *
   * @param log the log to append to
   */
  public GUIProjectViewMock(StringBuilder log) {
    this.log = log;
  }

  /**
   * Adds the given features of the controller to the view. This allows the view to interact with
   * the controller and tell it when the user has done something related to an action listener.
   *
   * @param features the features of the controller
   */
  @Override
  public void addFeatures(Features features) {
    log.append("addFeatures called").append(System.lineSeparator());
  }

  /**
   * Adds a new layer to the view's layer list with the given layer name.
   *
   * @param layerName the name of the layer
   */
  @Override
  public void addLayer(String layerName) {
    log.append("addLayer called").append(System.lineSeparator());
  }

  /**
   * Adds the given filters to the view's filter list so the user can see all available filters.
   *
   * @param filters the list of filter names
   */
  @Override
  public void addFilters(List<String> filters) {
    log.append("addFilters called").append(System.lineSeparator());

  }

  /**
   * Takes in a 2D array of pixels and turns it into a {@code BufferedImage} that the view with then
   * render to the user. This method will replace the existing image with the updated image if
   * called more than once.
   *
   * @param image the 2D array of pixels
   */
  @Override
  public void renderImage(PixelInterface[][] image) {
    log.append("renderImage called").append(System.lineSeparator());

  }

  /**
   * Given the String passed in, it will render a popup window with the message to the user.
   *
   * @param message the message to be displayed
   */
  @Override
  public void renderMessage(String message) {
    log.append("renderMessage called").append(System.lineSeparator());

  }

  /**
   * Activates all the buttons in the view once a project has been loaded in or created. This
   * prevents the user trying to add layers, filter layers, or adding images to layers without the
   * program being started.
   */
  @Override
  public void activateButtons() {
    log.append("activateButtons called").append(System.lineSeparator());


  }

  /**
   * Refreshes the entire view and repaints all the components.
   */
  @Override
  public void refresh() {
    log.append("refresh called").append(System.lineSeparator());

  }
}
