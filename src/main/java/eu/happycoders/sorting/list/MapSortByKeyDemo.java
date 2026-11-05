package eu.happycoders.sorting.list;

import java.util.Map;
import java.util.TreeMap;

public class MapSortByKeyDemo {
  static void main() {
    Map<String, Integer> scores = Map.of("Paul", 71, "Eva", 82, "Ben", 95, "Antonia", 88);

    Map<String, Integer> byName = new TreeMap<>(scores);
    System.out.println(byName);
  }
}
