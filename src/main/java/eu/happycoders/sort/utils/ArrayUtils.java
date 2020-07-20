package eu.happycoders.sort.utils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Array utilities for test program.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class ArrayUtils {

  /**
   * Creates an array of the given size, containing the values <code>0</code>
   * to <code>size-1</code> in ascending order.
   *
   * @param size the size
   * @return a sorted array of the given size
   */
  public static int[] createSortedArray(int size) {
    int[] elements = new int[size];
    for (int i = 0; i < size; i++) {
      elements[i] = i;
    }
    return elements;
  }

  /**
   * Creates an array of the given size, containing the values <code>0</code>
   * to <code>size-1</code> in descending order.
   *
   * @param size the size
   * @return a sorted array of the given size
   */
  public static int[] createReversedArray(int size) {
    int[] elements = new int[size];
    for (int i = 0; i < size; i++) {
      elements[size - 1 - i] = i;
    }
    return elements;
  }

  /**
   * Creates an array of the given size, containing random values from
   * <code>0</code> to <code>size-1</code>.
   *
   * @param size the size
   * @return a sorted array of the given size
   */
  public static int[] createRandomArray(int size) {
    Random rand = ThreadLocalRandom.current();
    int[] elements = new int[size];
    for (int i = 0; i < size; i++) {
      // Use size as upper bound,
      // so that we have a certain, but not high probability for duplicates
      elements[i] = rand.nextInt(size);
    }
    return elements;
  }

  /**
   * Swaps to elements inside an array.
   *
   * @param array the array
   * @param i     the index of the first element
   * @param j     the index of the second element
   */
  public static void swap(int[] array, int i, int j) {
    int help = array[i];
    array[i] = array[j];
    array[j] = help;
  }

  /**
   * Calculates the median of the given values.
   *
   * @param values the values
   * @return the median of the given values
   */
  public static long median(long[] values) {
    Arrays.sort(values);
    int length = values.length;
    int middle = length / 2;
    if (length % 2 == 0)
      return (values[middle] + values[middle - 1]) / 2;
    else
      return values[middle];
  }

  /**
   * Shuffles the given array.
   *
   * @param array the array
   */
  public static void shuffle(int[] array) {
    Random rand = ThreadLocalRandom.current();
    for (int i = 0; i < array.length; i++) {
      int randomIndexToSwap = rand.nextInt(array.length);
      int temp = array[randomIndexToSwap];
      array[randomIndexToSwap] = array[i];
      array[i] = temp;
    }
  }

  public static boolean isSorted(int[] elements) {
    for (int i = 0; i < elements.length - 1; i++) {
      if (elements[i] > elements[i + 1]) return false;
    }
    return true;
  }

}
