package controller.file;

import java.io.IOException;

/**
 * Represents a way for any subclass to read and write files of type {@code F}.
 *
 * @param <F> The file type.
 */
public interface FileHandler<F> {

  /**
   * This method get all the information in a file.
   *
   * @param filepath The filepath of a file.
   * @return All the information in a file.
   * @throws IOException if the file is invalid.
   */
  F readFile(String filepath) throws IOException;

  /**
   * This method read the information to a file.
   *
   * @param filepath the filepath of a file.
   * @throws IOException if the file is invalid.
   */
  void writeFile(String filepath) throws IOException;
}
