package eu.happycoders.sort.jdk.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StringListSortDemo {
  static void main() {
    List<String> names = new ArrayList<>(List.of("Paul", "Eva", "Ben", "Antonia"));

    names.sort(null);
    System.out.println(names);

    names.sort(Comparator.reverseOrder());
    System.out.println(names);
  }
}
