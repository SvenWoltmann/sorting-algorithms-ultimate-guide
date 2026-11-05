package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.List;

public class ImmutableListCopySortDemo {
  static void main() {
    List<String> names = List.of("Paul", "Eva", "Ben", "Antonia");

    List<String> sortedNames = new ArrayList<>(names);
    sortedNames.sort(null);

    System.out.println(sortedNames);
    System.out.println(names);
  }
}
