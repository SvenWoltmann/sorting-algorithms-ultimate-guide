package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.List;

public class StringListReversedViewDemo {
  static void main() {
    List<String> names = new ArrayList<>(List.of("Paul", "Eva", "Ben", "Antonia"));
    names.sort(null);

    List<String> descending = names.reversed();
    System.out.println(descending);
  }
}
