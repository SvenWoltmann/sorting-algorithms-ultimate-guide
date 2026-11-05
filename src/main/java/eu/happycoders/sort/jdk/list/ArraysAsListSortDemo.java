package eu.happycoders.sort.jdk.list;

import java.util.Arrays;
import java.util.List;

public class ArraysAsListSortDemo {
  static void main() {
    String[] array = {"Paul", "Eva", "Ben", "Antonia"};
    List<String> names = Arrays.asList(array);

    names.sort(null);

    System.out.println(names);
    System.out.println(Arrays.toString(array));
  }
}
