import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.Pixel;
import model.PixelInterface;
import org.junit.Test;

/**
 * Test class to ensure Pixel functionality works as expected.
 */
public class PixelTest {

  /**
   * Tests to ensure RGBA constructor will only allow for valid values between 0 and 255 to be input
   * when constructing an RGBA {@code Pixel}. Since RGB constructor uses RGBA constructor but
   * assigns the alpha to be 255, it is impossible to the alpha value to invalid.
   */
  @Test
  public void testRGBAPixelConstruction() {
    try { // valid values
      new Pixel(0, 0, 0, 0);
    } catch (IllegalArgumentException e) {
      fail("Exception was thrown");
    }
    try { // valid values
      new Pixel(100, 220, 50, 87);
    } catch (IllegalArgumentException e) {
      fail("Exception was thrown");
    }
    try { // red value to large
      new Pixel(500, 220, 50, 87);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // red value negative
      new Pixel(-20, 220, 50, 87);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // green value to large
      new Pixel(50, 500, 50, 87);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // green value negative
      new Pixel(50, -50, 50, 87);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // green value to large
      new Pixel(50, 50, 500, 87);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // blue value negative
      new Pixel(50, 50, -50, 87);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // alpha value to large
      new Pixel(50, 50, 5, 870);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try { // alpha value negative
      new Pixel(50, 50, 50, -87);
      fail("Exception was thrown");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

  /**
   * Test method to ensure all getter methods work properly and return the right pixel's field
   * value.
   */
  @Test
  public void testGetPixelValueMethods() {
    int r1 = 11;
    int g1 = 25;
    int b1 = 38;
    int a1 = 40;
    int r2 = 101;
    int g2 = 178;
    int b2 = 210;
    PixelInterface pRGBA = new Pixel(r1, g1, b1, a1);
    PixelInterface pRGB = new Pixel(r2, g2, b2);

    assertEquals(11, pRGBA.getRed());
    assertEquals(25, pRGBA.getGreen());
    assertEquals(38, pRGBA.getBlue());
    assertEquals(40, pRGBA.getAlpha());
    assertEquals(38, pRGBA.getValue());
    assertEquals(25, pRGBA.getIntensity());
    assertEquals(23, pRGBA.getLuma());

    assertEquals(101, pRGB.getRed());
    assertEquals(178, pRGB.getGreen());
    assertEquals(210, pRGB.getBlue());
    assertEquals(255, pRGB.getAlpha());
    assertEquals(210, pRGB.getValue());
    assertEquals(163, pRGB.getIntensity());
    assertEquals(164, pRGB.getLuma());
  }

  /**
   * Test method to ensure an RGBA {@code Pixel} is compressed to an RGB {@code Pixel}.
   */
  @Test
  public void convertToRGB() {
    int r1 = 101;
    int g1 = 78;
    int b1 = 41;
    int a1 = 200;
    PixelInterface pRGBA = new Pixel(r1, g1, b1, a1);
    PixelInterface compressedPixel = pRGBA.convertToRGB();
    assertEquals(79, compressedPixel.getRed());
    assertEquals(61, compressedPixel.getGreen());
    assertEquals(32, compressedPixel.getBlue());
  }

  /**
   * Test method to ensure the toString method will correctly return a {@code Pixel}'s
   * representation as a String.
   */
  @Test
  public void testToString() {
    int r1 = 101;
    int g1 = 78;
    int b1 = 41;
    int a1 = 200;
    PixelInterface pRGBA = new Pixel(r1, g1, b1, a1);
    assertEquals("101 78 41 200", pRGBA.toString(0));
    assertEquals("79 61 32", pRGBA.toString(1));
  }
}