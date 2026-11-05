package eu.happycoders.sort.jdk.list;

import java.util.List;

public class StringListStreamSortedDemo {
  static void main() {
    List<String> names = List.of("Paul", "Eva", "Ben", "Antonia");

    List<String> sortedNames = names.stream().sorted().toList();

    System.out.println(sortedNames);
    System.out.println(names);
  }
}
