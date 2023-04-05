import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import controller.CollagerCommand;
import controller.commands.LoadProject;
import model.Layer;
import model.LayerInterface;
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
    ProjectModel projectValid = new Project();

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
    // Valid width and height for computer screen
    projectValid.newProject(1000, 1000);
    assertEquals(1000, projectValid.getHeight());
    assertEquals(1000, projectValid.getWidth());
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

    p2.newProject(500, 500);
    p2.addLayer("layer-1");
    try {
      p2.addLayer("layer-1");
      fail("Exception not thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    assertEquals("layer-1", p2.getLayers().get("layer-1").getName());
    p2.addLayer("layer-2");
    assertEquals("layer-2", p2.getLayers().get("layer-2").getName());

    String filepath = "projects/sample1.collage";
    ProjectView view = new CommandLineTextView();
    CollagerCommand loadProject = new LoadProject(filepath);
    loadProject.execute(p2, view);
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
    StringBuilder result = new StringBuilder();
    StringBuilder answer = new StringBuilder();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result.append(imageGrid[i][j].toString(0));
        answer.append(resultGrid[i][j].toString(0));
      }
    }
    assertEquals(result.toString(), answer.toString());
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

  /**
   * Test for compressLayers() method.
   */
  @Test
  public void testForCompressLayers() {
    //The image grid for layer 1
    PixelInterface[][] imageGrid1 = {{new Pixel(50, 50, 50, 50),
            new Pixel(60, 60, 60, 60)},
            {new Pixel(100, 100, 100, 100),
                    new Pixel(200, 200, 200, 200)}};

    //The image grid for layer 2
    PixelInterface[][] imageGrid2 = {{new Pixel(50, 50, 50, 50),
            new Pixel(60, 60, 60, 60)},
            {new Pixel(100, 100, 100, 100),
                    new Pixel(200, 200, 200, 200)}};

    ProjectModel p1 = new Project();
    //test whether the compressLayers() method can be used when haven't created
    //new project.
    try {
      p1.compressLayers();
      fail("exception not be thrown");
    } catch (IllegalStateException e) {
      //do nothing
    }

    p1.newProject(100, 100);
    p1.addLayer("L1");
    p1.addImageToLayer("L1", imageGrid1, 0, 0);

    p1.addLayer("L2");
    p1.addImageToLayer("L2", imageGrid2, 1, 1);

    //The image grid for answer
    PixelInterface[][] resultGrid = {{new Pixel(215, 215, 215, 255),
            new Pixel(209, 209, 209, 255)},
            {new Pixel(194, 194, 194, 255),
                    new Pixel(180, 180, 180, 255)}};

    PixelInterface[][] compressedLayer = p1.compressLayers();
    String result = "";
    String answer = "";
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += compressedLayer[i][j].toString(0);
        answer += resultGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  @Test
  public void testForGetLayers() {
    ProjectModel p1 = new Project();
    //test whether the getLayers() method can be used when haven't created
    //new project.
    try {
      p1.getLayers();
      fail("exception not be thrown");
    } catch (IllegalStateException e) {
      //do nothing
    }

    PixelInterface[][] bg = {{new Pixel(255, 255, 255, 50),
            new Pixel(255, 255, 255, 60)},
            {new Pixel(255, 255, 255, 100),
                    new Pixel(255, 255, 255, 200)}};

    p1.newProject(2, 2);
    p1.addLayer("L1");
    p1.addLayer("L2");
    p1.addLayer("L3");
    Map<String, LayerInterface> layerList = new LinkedHashMap<>();
    layerList.put("L1",
            new Layer("L1", bg));
    layerList.put("L2",
            new Layer("L2", bg));
    layerList.put("L3",
            new Layer("L3", bg));


    assertEquals(layerList.get(0), p1.getLayers().get(0));
    assertEquals(layerList.get(1), p1.getLayers().get(1));
    assertEquals(layerList.get(2), p1.getLayers().get(2));
  }

  @Test
  public void testForExportProject() {
    ProjectModel p1 = new Project();
    p1.newProject(2, 2);
    assertEquals("C1\n" +
            "2 2\n" +
            "255\n" +
            "bg normal\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n", p1.exportProject());

    PixelInterface[][] imageGrid1 = {{new Pixel(50, 50, 50, 50),
            new Pixel(60, 60, 60, 60)},
            {new Pixel(100, 100, 100, 100),
                    new Pixel(200, 200, 200, 200)}};

    //The image grid for layer 2
    PixelInterface[][] imageGrid2 = {{new Pixel(50, 50, 50, 50),
            new Pixel(60, 60, 60, 60)},
            {new Pixel(100, 100, 100, 100),
                    new Pixel(200, 200, 200, 200)}};


    p1.addLayer("L1");
    p1.addImageToLayer("L1", imageGrid1, 0, 0);

    p1.addLayer("L2");
    p1.addImageToLayer("L2", imageGrid2, 1, 1);

    assertEquals("C1\n" +
            "2 2\n" +
            "255\n" +
            "bg normal\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "L1 normal\n" +
            "50 50 50 50\n" +
            "60 60 60 60\n" +
            "100 100 100 100\n" +
            "200 200 200 200\n" +
            "L2 normal\n" +
            "255 255 255 0\n" +
            "255 255 255 0\n" +
            "255 255 255 0\n" +
            "50 50 50 50\n", p1.exportProject());
  }

  @Test
  public void testForBuildProject() {
    ProjectModel p1 = new Project();
    p1.buildProject("C1\n" +
            "2 2\n" +
            "255\n" +
            "bg normal\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "L1 normal\n" +
            "50 50 50 50\n" +
            "60 60 60 60\n" +
            "100 100 100 100\n" +
            "200 200 200 200\n" +
            "L2 normal\n" +
            "255 255 255 0\n" +
            "255 255 255 0\n" +
            "255 255 255 0\n" +
            "50 50 50 50\n");
    Map<String, LayerInterface> layerList = new LinkedHashMap<>();
    PixelInterface[][] bg = {{new Pixel(255, 255, 255, 50),
            new Pixel(255, 255, 255, 60)},
            {new Pixel(255, 255, 255, 100),
                    new Pixel(255, 255, 255, 200)}};
    layerList.put("bg",
            new Layer("bg", bg));
    layerList.put("L1",
            new Layer("L1", bg));
    layerList.put("L2",
            new Layer("L2", bg));
    assertEquals(layerList.get("bg").getName(), p1.getLayers().get("bg").getName());
    assertEquals(layerList.get("L1").getName(), p1.getLayers().get("L1").getName());
    assertEquals(layerList.get("L2").getName(), p1.getLayers().get("L2").getName());

    assertEquals(2, p1.getHeight());
    assertEquals(2, p1.getWidth());
  }
}