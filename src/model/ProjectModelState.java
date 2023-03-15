package model;

import java.util.Map;

public interface ProjectModelState<P> {

  int getWidth();
  int getHeight();
  int getMaxValue();
  P getPixelAtPos(int row, int col);
  P getPixelAtPos(Position pos);

  //Map<Layer, Filter> getLayers();

}
