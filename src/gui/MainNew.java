package gui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainNew {

  public static void main(String[] args) {
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.put("d", 4);
    map.put("e", 5);
    map.put("f", 6);

    Set<String> keys = map.keySet();
    for (String key : keys) {
      System.out.println(map.get(key));
    }
  }

}
