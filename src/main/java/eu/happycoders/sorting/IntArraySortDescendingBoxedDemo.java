package eu.happycoders.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class IntArraySortDescendingBoxedDemo {
  static void main() {
    int[] a = {4, 8, 5, 9, 2, 3, 1, 7, 6};
    int[] sorted =
        IntStream.of(a)
            .boxed()
            .sorted(Comparator.reverseOrder())
            .mapToInt(Integer::intValue)
            .toArray();
    System.out.println(Arrays.toString(sorted));
  }
}
