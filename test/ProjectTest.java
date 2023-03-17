import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import model.Layer;
import model.Project;
import model.ProjectModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for testing a {@code Project}'s behaviors and functionality.
 */
public class ProjectTest {

  /**
   * Method to test that a project is created with a valid width and height. Also testing to ensure
   * user cannot create project with invalid heights and widths.
   */
  @Test
  public void testNewProjectCreation() {
    ProjectModel project1 = new Project();
    ProjectModel projectInvalid1 = new Project();
    ProjectModel projectInvalid2 = new Project();
    ProjectModel projectInvalid3 = new Project();
    ProjectModel projectInvalidScreenSize = new Project();

    project1.newProject(500, 500);
    assertEquals(500, project1.getHeight());
    assertEquals(500, project1.getWidth());

    try { // invalid width
      projectInvalid1.newProject(-100, 200);
      fail("Exception not thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // invalid height
      projectInvalid2.newProject(100, -200);
      fail("Exception not thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // invalid width and height
      projectInvalid3.newProject(-100, -200);
      fail("Exception not thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // invalid width and height for computer screen
      projectInvalid3.newProject(10000000, 10000000);
      fail("Exception not thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    //TODO: make sure layer list has bg layer in in after creating project

  }

  /**
   * Test method to ensure layers are added correctly and ensure users cannot call this method
   * before a project is created. Also testing to ensure a null layer cannot be passed in as an
   * argument.
   */
  @Test
  public void testAddLayer() {
    ProjectModel p1 = new Project();
    p1.newProject(500, 500);

    ProjectModel p2 = new Project();

    try { // IllegalState checking
      p2.addLayer("layer-1");
      fail("Exception not thrown");
    } catch (IllegalStateException e) {
      // do nothing
    }
    //TODO: add test for ensuring exception is thrown for trying to add a layer that already exists
    //TODO: add test for valid layer adding

    //TODO: add test for valid layer adding after loading in project
    //TODO: add test for ensuring layer cannot be added if it already exists

  }

  /**
   * Test method to ensure an image is imported correctly and added to the given layer correctly.
   * Also tests functionality to handle invalid filepath and invalid call to method.
   */
  @Test
  public void testAddImageToLayer() {

  }

}