import static org.junit.Assert.assertEquals;

import model.Layer;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;
import org.junit.Test;

/**
 * Test class for testing all functionality specific to a layer.
 */
public class LayerTest {

  /**
   * Test method to ensure the {@code Layer}'s name field is returned.
   */
  @Test
  public void getName() {
    LayerInterface layer = new Layer("testLayer");
    assertEquals("testLayer", layer.getName());
  }

  /**
   * Test method getFilter.
   */
  @Test
  public void getFilter() {
    LayerInterface layer = new Layer("testLayer");
    assertEquals("normal", layer.getFilter());
  }

  /**
   * Test method setFilter to see whether we can set the filter correctly.
   */
  @Test
  public void setFilter() {
    LayerInterface layer = new Layer("testLayer");
    layer.setFilter("blue-filter");
    assertEquals("blue-filter", layer.getFilter());

    layer.setFilter("green-filter");
    assertEquals("green-filter", layer.getFilter());
  }

  /**
   * Test method getPixelGrid to see whether we can get the pixel grid correctly.
   */
  @Test
  public void getPixelGrid() {
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60),
        new Pixel(70, 70, 70, 70)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200),
            new Pixel(255, 255, 255, 255)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    PixelInterface[][] newGrid = layer.getPixelGrid();
    StringBuilder result = new StringBuilder();
    StringBuilder answer = new StringBuilder();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        result.append(pixelGrid[i][j].toString(0));
        answer.append(newGrid[i][j].toString(0));
      }
    }
    assertEquals(result.toString(), answer.toString());
  }
}