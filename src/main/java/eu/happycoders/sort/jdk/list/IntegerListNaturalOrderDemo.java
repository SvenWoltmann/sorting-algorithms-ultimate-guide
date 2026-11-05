package eu.happycoders.sort.jdk.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IntegerListNaturalOrderDemo {
  static void main() {
    List<Integer> numbers = new ArrayList<>(List.of(4, 8, 5, 9, 2, 3, 1, 7, 6));
    numbers.sort(Comparator.naturalOrder());
    System.out.println(numbers);
  }
}
