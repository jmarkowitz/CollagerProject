package model;

import java.util.function.Function;

public interface FilterInterface extends Function<LayerInterface, LayerInterface> {

  @Override
  LayerInterface apply(LayerInterface layer);

  String getFilterName();
}
