package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringListStreamToCollectionDemo {
  static void main() {
    List<String> names = List.of("Paul", "Eva", "Ben", "Antonia");

    List<String> sortedNames =
        names.stream().sorted().collect(Collectors.toCollection(ArrayList::new));

    sortedNames.add("Zoe");

    System.out.println(sortedNames);
  }
}
