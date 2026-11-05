package eu.happycoders.sorting.list;

import java.util.List;

public class ImmutableListSortDemo {
  static void main() {
    List<String> names = List.of("Paul", "Eva", "Ben", "Antonia");

    // throws an UnsupportedOperationException – the list is immutable
    names.sort(null);
  }
}
