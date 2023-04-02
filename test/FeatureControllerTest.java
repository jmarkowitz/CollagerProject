import static org.junit.Assert.*;

import gui.FeatureController;
import gui.GUIProjectView;
import model.PixelInterface;
import model.ProjectModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class FeatureControllerTest {

  private FeatureController featureController;
  private ProjectModel projectModel;
  private GUIProjectView guiProjectView;

  @Before
  public void setUp() {
    projectModel = Mockito.mock(ProjectModel.class);
    guiProjectView = Mockito.mock(GUIProjectView.class);
    featureController = new FeatureController(projectModel, guiProjectView);
  }

  @Test
  public void testNewProject() {
    featureController.newProject(100, 200);
    Mockito.verify(projectModel).newProject(100, 200);
    Mockito.verify(guiProjectView).renderImage(Mockito.any(PixelInterface[][].class));
    Mockito.verify(guiProjectView).activateButtons();
    assertTrue(featureController.isProjectActive);
  }

  @Test
  public void testLoadProject() {
    featureController.loadProject("test_file_path");
    Mockito.verify(projectModel).buildProject(Mockito.anyString());
    Mockito.verify(guiProjectView).renderMessage(Mockito.anyString());
  }

  // Add more tests for other methods as needed

  @Test
  public void testStringBuilderMock() {
    StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);
    stringBuilder.append("Method was called.");
    Mockito.verify(stringBuilder).append(Mockito.anyString());
  }
}
