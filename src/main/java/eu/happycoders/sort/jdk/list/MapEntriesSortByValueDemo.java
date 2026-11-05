package eu.happycoders.sort.jdk.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapEntriesSortByValueDemo {
  static void main() {
    Map<String, Integer> scores = Map.of("Paul", 71, "Eva", 82, "Ben", 95, "Antonia", 88);

    List<Map.Entry<String, Integer>> entries = new ArrayList<>(scores.entrySet());
    entries.sort(Map.Entry.comparingByValue());
    System.out.println(entries);
  }
}
