package eu.happycoders.sorting;

import java.util.Arrays;
import java.util.Comparator;

public class StringArraySortDescendingDemo {
  static void main() {
    String[] names = {"Susan", "Thomas", "Judith", "Daniel", "Eva", "Ben", "Antonia", "Paul"};
    Arrays.sort(names, Comparator.reverseOrder());
    System.out.println(Arrays.toString(names));
  }
}
