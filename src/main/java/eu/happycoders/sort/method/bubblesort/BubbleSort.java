package eu.happycoders.sort.method.bubblesort;

import eu.happycoders.sort.method.*;

/**
 * Bubble Sort implementation for performance tests.
 *
 * <p>
 * Unoptimized variant.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BubbleSort implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    int numElements = elements.length;
    for (; ; ) {
      boolean swapped = false;
      for (int i = 0; i < numElements - 1; i++) {
        int left = elements[i];
        int right = elements[i + 1];
        if (left > right) {
          elements[i + 1] = left;
          elements[i] = right;
          swapped = true;
        }
      }
      if (!swapped) break;
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    int numElements = elements.length;
    for (; ; ) {
      counters.incIterations();

      boolean swapped = false;
      for (int i = 0; i < numElements - 1; i++) {
        counters.incIterations();

        int left = elements[i];
        int right = elements[i + 1];
        counters.addReads(2);
        counters.incComparisons();
        if (left > right) {
          elements[i + 1] = left;
          elements[i] = right;
          counters.addWrites(2);
          swapped = true;
        }
      }
      if (!swapped) break;
    }
  }

}
