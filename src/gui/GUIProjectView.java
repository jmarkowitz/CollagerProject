package gui;

import java.util.List;
import model.PixelInterface;

public interface GUIProjectView {

  void addFeatures(Features features);

  void addLayer(String layerName);
  void addFilters(List<String> filters);

  void renderImage(PixelInterface[][] image);

  void renderMessage(String message);

}
