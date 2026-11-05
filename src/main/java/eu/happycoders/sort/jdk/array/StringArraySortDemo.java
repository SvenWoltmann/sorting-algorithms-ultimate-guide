package eu.happycoders.sort.jdk.array;

import java.util.Arrays;

public class StringArraySortDemo {
  static void main() {
    String[] names = {"Susan", "Thomas", "Judith", "Daniel", "Eva", "Ben", "Antonia", "Paul"};
    Arrays.sort(names);
    System.out.println(Arrays.toString(names));
  }
}
