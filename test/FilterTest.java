import org.junit.Test;

import model.FilterInterface;
import model.Layer;
import model.LayerInterface;
import model.Pixel;
import model.PixelInterface;
import model.filters.BlueFilter;
import model.filters.BrightenIntensityFilter;
import model.filters.BrightenLumaFilter;
import model.filters.BrightenValueFilter;
import model.filters.DarkenIntensityFilter;
import model.filters.DarkenLumaFilter;
import model.filters.DarkenValueFilter;
import model.filters.GreenFilter;
import model.filters.NormalFilter;
import model.filters.RedFilter;

import static org.junit.Assert.assertEquals;


/**
 * The test for filter.
 */
public class FilterTest {

  /**
   * test for apply BlueFilter.
   */
  @Test
  public void testApplyForBlueFilter() {
    FilterInterface blueFilter = new BlueFilter(2, 3);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60),
        new Pixel(70, 70, 70, 70)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200),
            new Pixel(255, 255, 255, 255)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = blueFilter.apply(layer.getPixelGrid(), layer.getPixelGrid());
    PixelInterface[][] blueGrid = {{new Pixel(0, 0, 50, 50),
        new Pixel(0, 0, 60, 60),
        new Pixel(0, 0, 70, 70)},
        {new Pixel(0, 0, 100, 100),
            new Pixel(0, 0, 200, 200),
            new Pixel(0, 0, 255, 255)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        result += finalGrid[i][j].toString(0);
        answer += blueGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply GreenFilter.
   */
  @Test
  public void testApplyForGreenFilter() {
    FilterInterface greenFilter = new GreenFilter(2, 3);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60),
        new Pixel(70, 70, 70, 70)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200),
            new Pixel(255, 255, 255, 255)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = greenFilter.apply(layer, );
    PixelInterface[][] greenGrid = {{new Pixel(0, 50, 0, 50),
        new Pixel(0, 60, 0, 60),
        new Pixel(0, 70, 0, 70)},
        {new Pixel(0, 100, 0, 100),
            new Pixel(0, 200, 0, 200),
            new Pixel(0, 255, 0, 255)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        result += finalGrid[i][j].toString(0);
        answer += greenGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply RedFilter.
   */
  @Test
  public void testApplyForRedFilter() {
    FilterInterface redFilter = new RedFilter(2, 3);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60),
        new Pixel(70, 70, 70, 70)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200),
            new Pixel(255, 255, 255, 255)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = redFilter.apply(layer, );
    PixelInterface[][] redGrid = {{new Pixel(50, 0, 0, 50),
        new Pixel(60, 0, 0, 60),
        new Pixel(70, 0, 0, 70)},
        {new Pixel(100, 0, 0, 100),
            new Pixel(200, 0, 0, 200),
            new Pixel(255, 0, 0, 255)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        result += finalGrid[i][j].toString(0);
        answer += redGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply BrightenIntensityFilter.
   */
  @Test
  public void testApplyForBrightenIntensityFilter() {
    FilterInterface briIntensityFilter = new BrightenIntensityFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = briIntensityFilter.apply(layer, );
    PixelInterface[][] biGrid = {{new Pixel(100, 100, 100, 100),
        new Pixel(120, 120, 120, 120)},
        {new Pixel(200, 200, 200, 200),
            new Pixel(255, 255, 255, 255)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += biGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply DarkenFilter.
   */
  @Test
  public void testApplyForDarkenIntensityFilter() {
    FilterInterface darkIntensityFilter = new DarkenIntensityFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = darkIntensityFilter.apply(layer, );
    PixelInterface[][] diGrid = {{new Pixel(0, 0, 0, 0),
        new Pixel(0, 0, 0, 0)},
        {new Pixel(0, 0, 0, 0),
            new Pixel(0, 0, 0, 0)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += diGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply BrightenLumaFilter.
   */
  @Test
  public void testApplyForBrightenLumaFilter() {
    FilterInterface briLumaFilter = new BrightenLumaFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = briLumaFilter.apply(layer, );
    PixelInterface[][] blGrid = {{new Pixel(100, 100, 100, 100),
        new Pixel(120, 120, 120, 120)},
        {new Pixel(200, 200, 200, 200),
            new Pixel(255, 255, 255, 255)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += blGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply DarkenLumaFilter.
   */
  @Test
  public void testApplyForDarkenLumaFilter() {
    FilterInterface darkLumaFilter = new DarkenLumaFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = darkLumaFilter.apply(layer, );
    PixelInterface[][] dkGrid = {{new Pixel(0, 0, 0, 0),
        new Pixel(0, 0, 0, 0)},
        {new Pixel(0, 0, 0, 0),
            new Pixel(0, 0, 0, 0)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += dkGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply BrightenValueFilter.
   */
  @Test
  public void testApplyForBrightenValueFilter() {
    FilterInterface briValueFilter = new BrightenValueFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = briValueFilter.apply(layer, );
    PixelInterface[][] bvGrid = {{new Pixel(100, 100, 100, 100),
        new Pixel(120, 120, 120, 120)},
        {new Pixel(200, 200, 200, 200),
            new Pixel(255, 255, 255, 255)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += bvGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply DarkenValueFilter.
   */
  @Test
  public void testApplyForDarkenValueFilter() {
    FilterInterface darkValueFilter = new DarkenValueFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = darkValueFilter.apply(layer, );
    PixelInterface[][] dvGrid = {{new Pixel(0, 0, 0, 0),
        new Pixel(0, 0, 0, 0)},
        {new Pixel(0, 0, 0, 0),
            new Pixel(0, 0, 0, 0)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += dvGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply NormalFilter.
   */
  @Test
  public void testApplyForNormalFilter() {
    FilterInterface normalFilter = new NormalFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 50, 50),
        new Pixel(60, 60, 60, 60)},
        {new Pixel(100, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = normalFilter.apply(layer, );
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += pixelGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * test for apply DarkenValueFilter but the value is less than 0 so it should be. instead of 0.
   */
  @Test
  public void testApplyForDarkenValueFilterButValueIsLessThan0() {
    FilterInterface darkValueFilter = new DarkenValueFilter(2, 2);
    PixelInterface[][] pixelGrid = {{new Pixel(50, 50, 60, 50),
        new Pixel(60, 100, 60, 60)},
        {new Pixel(200, 100, 100, 100),
            new Pixel(200, 200, 200, 200)}};
    LayerInterface layer = new Layer("testLayer", pixelGrid);
    layer = darkValueFilter.apply(layer, );
    PixelInterface[][] dvGrid = {{new Pixel(0, 0, 0, 0),
        new Pixel(0, 0, 0, 0)},
        {new Pixel(0, 0, 0, 0),
            new Pixel(0, 0, 0, 0)}};
    String result = "";
    String answer = "";
    PixelInterface[][] finalGrid = layer.getPixelGrid();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        result += finalGrid[i][j].toString(0);
        answer += dvGrid[i][j].toString(0);
      }
    }
    assertEquals(result, answer);
  }

  /**
   * Test for getPFilterName.
   */
  @Test
  public void testForGetFilterName() {
    FilterInterface darkValueFilter = new DarkenValueFilter(2, 2);
    String result1 = darkValueFilter.getFilterName();
    assertEquals("darken-value", result1);

    FilterInterface blueFilter = new BlueFilter(2, 2);
    String result2 = blueFilter.getFilterName();
    assertEquals("blue-component", result2);

    FilterInterface greenFilter = new GreenFilter(2, 2);
    String result3 = greenFilter.getFilterName();
    assertEquals("green-component", result3);

    FilterInterface redValueFilter = new RedFilter(2, 2);
    String result4 = redValueFilter.getFilterName();
    assertEquals("red-component", result4);

    FilterInterface brightenValueFilter = new BrightenValueFilter(2, 2);
    String result5 = brightenValueFilter.getFilterName();
    assertEquals("brighten-value", result5);

    FilterInterface brightenIntensityFilter = new BrightenIntensityFilter(2, 2);
    String result6 = brightenIntensityFilter.getFilterName();
    assertEquals("brighten-intensity", result6);

    FilterInterface darkenIntensityFilter = new DarkenIntensityFilter(2, 2);
    String result7 = darkenIntensityFilter.getFilterName();
    assertEquals("darken-intensity", result7);

    FilterInterface brightenLumaFilter = new BrightenLumaFilter(2, 2);
    String result8 = brightenLumaFilter.getFilterName();
    assertEquals("brighten-luma", result8);

    FilterInterface darkenLumaFilter = new DarkenLumaFilter(2, 2);
    String result9 = darkenLumaFilter.getFilterName();
    assertEquals("darken-luma", result9);
  }
}