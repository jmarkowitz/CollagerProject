package controller.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * A utility class that reads a file and returns it as a string.
 */
public class FileUtil {

  /**
   * Reads a file and returns it as a string.
   *
   * @param filename the name of the file to be read
   * @return the file as a string
   */
  public static String readFileAsString(String filename) throws IOException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IOException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    return builder.toString();
  }

  public static String getFileExtension(String filepath) {
    int dotIndex = filepath.lastIndexOf('.');
    if (dotIndex == -1 || dotIndex == filepath.length() - 1) {
      return "";
    } else {
      return filepath.substring(dotIndex + 1).toLowerCase();
    }
  }

}
