package controller.file;

import java.io.IOException;

public interface FileHandler<F> {

  F readFile(String filepath) throws IOException;

  void writeFile(String filepath) throws IOException;
}
