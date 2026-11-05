package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListWithNullSortDemo {
  static void main() {
    List<String> names = new ArrayList<>(Arrays.asList("Paul", null, "Eva", "Ben"));

    // throws a NullPointerException – the natural order cannot compare null
    names.sort(null);
  }
}
