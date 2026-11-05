package eu.happycoders.sort.jdk.array;

import java.util.Arrays;

public class IntArrayRangeSortDemo {
  static void main() {
    int[] a = {4, 8, 5, 9, 2, 3, 1, 7, 6};
    Arrays.sort(a, 0, 5);
    System.out.println(Arrays.toString(a));
  }
}
