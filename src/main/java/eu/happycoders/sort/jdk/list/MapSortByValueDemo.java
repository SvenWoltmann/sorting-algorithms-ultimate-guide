package eu.happycoders.sort.jdk.list;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapSortByValueDemo {
  static void main() {
    Map<String, Integer> scores = Map.of("Paul", 71, "Eva", 82, "Ben", 95, "Antonia", 88);

    Map<String, Integer> byScore =
        scores.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

    System.out.println(byScore);
  }
}
