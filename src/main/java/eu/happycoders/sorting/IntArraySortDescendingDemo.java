package eu.happycoders.sorting;

import java.util.Arrays;

public class IntArraySortDescendingDemo {
  static void main() {
    int[] a = {4, 8, 5, 9, 2, 3, 1, 7, 6};
    Arrays.sort(a);
    reverse(a);
    System.out.println(Arrays.toString(a));
  }

  private static void reverse(int[] a) {
    for (int i = 0, j = a.length - 1; i < j; i++, j--) {
      int tmp = a[i];
      a[i] = a[j];
      a[j] = tmp;
    }
  }
}
