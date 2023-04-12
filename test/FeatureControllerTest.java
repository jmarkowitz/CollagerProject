import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import gui.controller.FeatureController;
import gui.controller.Features;
import gui.view.GUIProjectView;
import model.Project;
import model.ProjectModel;
import org.junit.Test;

/**
 * Tests for testing FeatureController class and its methods.
 */
public class FeatureControllerTest {


  /**
   * Test method to ensure the controller is constructed correctly.
   */
  @Test
  public void testValidControllerConstruction() {
    ProjectModel model = new Project();
    model.newProject(1, 1);
    GUIProjectView view = new GUIProjectViewMock(new StringBuilder());
    try {
      Features gc = new FeatureController(model, view);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Test method to ensure IllegalArgumentExceptions are thrown if the controller is given a null
   * model or view.
   */
  @Test
  public void testInvalidControllerConstruction() {
    ProjectModel model = new Project();
    model.newProject(1, 1);
    GUIProjectView view = new GUIProjectViewMock(new StringBuilder());
    try { // all null params
      new FeatureController(null, null);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // all null but model
      new FeatureController(model, null);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // all null but view
      new FeatureController(null, view);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

  /**
   * Tests that the methods are called in the correct order when a new project is created.
   */
  @Test
  public void testMethodsCalledInCorrectOrderForNewProject() {
    String expected = "addFeatures called\n"
        + "addFilters called\n"
        + "refresh called\n"
        + "renderImage called\n"
        + "activateButtons called\n"
        + "refresh called\n"
        + "addLayer called\n"
        + "refresh called\n"
        + "renderImage called\n"
        + "refresh called\n"
        + "renderImage called\n"
        + "refresh called\n"
        + "renderMessage called\n"
        + "refresh called\n"
        + "renderMessage called\n"
        + "refresh called\n";
    ProjectModel model = new Project();
    StringBuilder log = new StringBuilder();
    GUIProjectViewMock view = new GUIProjectViewMock(log);
    FeatureController controller = new FeatureController(model, view);
    controller.newProject(1, 1);
    controller.addLayer("layer1");
    controller.addImageToLayer("layer1", "res/thanos_truck.ppm", 0, 0);
    controller.setFilter("layer1", "red-component");
    controller.saveProject("projects/sample2.collage");
    controller.saveImageAs("projects/sample2.png");
    assertEquals(expected, log.toString());
  }

  /**
   * Tests that the methods are called in the correct order when a project is loaded.
   */
  @Test
  public void testMethodsCalledInCorrectOrderForOpenProject() {
    String expected = "addFeatures called\n"
        + "addLayer called\n"
        + "addFilters called\n"
        + "refresh called\n"
        + "renderImage called\n"
        + "activateButtons called\n"
        + "refresh called\n"
        + "addLayer called\n"
        + "refresh called\n"
        + "renderImage called\n"
        + "refresh called\n"
        + "renderImage called\n"
        + "refresh called\n"
        + "renderMessage called\n"
        + "refresh called\n"
        + "renderMessage called\n"
        + "refresh called\n";
    ProjectModel model = new Project();
    StringBuilder log = new StringBuilder();
    GUIProjectViewMock view = new GUIProjectViewMock(log);
    FeatureController controller = new FeatureController(model, view);
    controller.loadProject("projects/sample2.collage");
    controller.addLayer("layer2");
    controller.addImageToLayer("layer2", "res/thanos_truck.ppm", 0, 0);
    controller.setFilter("layer2", "red-component");
    controller.saveProject("projects/sample3.collage");
    controller.saveImageAs("projects/sample3.png");
    assertEquals(expected, log.toString());
  }
}
