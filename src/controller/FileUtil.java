package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {

  public static String readFileAsString(String filename) {
    String workingDir = System.getProperty("user.dir");
    String fullPath = workingDir + File.separator + filename;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(fullPath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fullPath + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    System.out.println(builder.toString());
    return builder.toString();
  }

}
