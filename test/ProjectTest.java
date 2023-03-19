import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import controller.CollagerCommand;
import controller.commands.LoadProject;
import model.Pixel;
import model.PixelInterface;
import model.Project;
import model.ProjectModel;
import view.CommandLineTextView;
import view.ProjectView;

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

  }

  /**
   * Test method to ensure layers are added correctly and ensure users cannot call this method
   * before a project is created. Also testing to ensure a null layer cannot be passed in as an
   * argument.
   */
  @Test
  public void testAddLayer() throws IOException {
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
    p2.newProject(500, 500);
    p2.addLayer("layer-1");
    try {
      p2.addLayer("layer-1");
      fail("Exception not thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    //TODO: add test for valid layer adding
    assertEquals("layer-1", p2.getLayers().get("layer-1").getName());
    p2.addLayer("layer-2");
    assertEquals("layer-2", p2.getLayers().get("layer-2").getName());

    //TODO: add test for valid layer adding after loading in project

    ProjectView view = new CommandLineTextView();
    Readable in = new StringReader("projects/sample1.collage");
    Scanner s = new Scanner(in);
    CollagerCommand loadProject = new LoadProject(s, view);
    loadProject.execute(p2);
    assertEquals("layer3", p2.getLayers().get("layer3").getName());
    p2.addLayer("layer-4");
    assertEquals("layer-4", p2.getLayers().get("layer-4").getName());
  }

  /**
   * Test method to ensure an image is imported correctly and added to the given layer correctly.
   * Also tests functionality to handle invalid filepath and invalid call to method.
   */
  @Test
  public void testAddImageToLayer() {
    PixelInterface[][] imageGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};

    //if the new project hasn't been created
    ProjectModel p1 = new Project();
    try {
      p1.addImageToLayer("layer1", imageGrid, 2, 2);
      fail("exception not be thrown");
    } catch (IllegalStateException e) {
      //do nothing
    }
    //if layerLInkedMap doesn't contain the layer name
    p1.newProject(2, 2);
    try {
      p1.addImageToLayer("layer1", imageGrid, 4, 4);
      fail("exception not be thrown");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
    p1.addLayer("layer1");
    //if the width is invalid
    try {
      p1.addImageToLayer("layer1", imageGrid, 4, 1);
      fail("exception not be thrown");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
    //if the height is invalid
    try {
      p1.addImageToLayer("layer1", imageGrid, 1, 4);
      fail("exception not be thrown");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
    //add image successfully
    p1.addImageToLayer("layer1", imageGrid, 0, 0);
    PixelInterface[][] resultGrid = p1.getLayers().get("layer1").getPixelGrid();
    String result = "";
    String answer = "";
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += imageGrid[i][j].toString(0);
        answer += resultGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * Test for SetFilter.
   */
  @Test
  public void testForSetFilter() {
    //if the new project hasn't been created
    ProjectModel p1 = new Project();
    try {
      p1.setFilter("layer1", "blue-filter");
      fail("exception not be thrown");
    } catch (IllegalStateException e) {
      //do nothing
    }
    //if layerLInkedMap doesn't contain the layer name
    p1.newProject(2, 2);
    try {
      p1.setFilter("layer1", "blue-filter");
      fail("exception not be thrown");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
    p1.addLayer("layer1");
    //set filter succesfully
    p1.setFilter("layer1", "blue-component");
    assertEquals("blue-component", p1.getLayers().get("layer1").getFilter());
  }

  /**
   * Test for getWidth and getHeight.
   */
  @Test
  public void testForGetWidthAndGetHeight() {
    ProjectModel p1 = new Project();
    try {
      p1.getHeight();
      fail("exception not be thrown");
    } catch (IllegalStateException e) {
      //do nothing
    }
    try {
      p1.getWidth();
      fail("exception not be thrown");
    } catch (IllegalStateException e) {
      //do nothing
    }
    p1.newProject(1, 1);
    assertEquals(1, p1.getWidth());
    assertEquals(1, p1.getHeight());
  }
}