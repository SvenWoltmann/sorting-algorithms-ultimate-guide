package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StringListNullsLastDemo {
  static void main() {
    List<String> names = new ArrayList<>(Arrays.asList("Paul", null, "Eva", "Ben"));
    names.sort(Comparator.nullsLast(Comparator.naturalOrder()));
    System.out.println(names);
  }
}
