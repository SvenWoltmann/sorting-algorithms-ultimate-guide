package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.List;

public class BrokenComparatorDemo {
  static void main() {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < 32; i++) {
      numbers.add(i % 2);
    }

    // wrong – never returns 0 for equal numbers
    numbers.sort((a, b) -> a < b ? -1 : 1);
  }
}
