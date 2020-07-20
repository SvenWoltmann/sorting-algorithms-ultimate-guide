package eu.happycoders.sort.method.bubblesort;

import eu.happycoders.sort.method.*;

/**
 * Bubble Sort implementation for performance tests.
 *
 * <p>
 * Optimized: in each iteration, more than one element can be placed in its
 * final position; we assume all elements after the last swap to be sorted.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BubbleSortOpt2 implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    int max = elements.length - 1;
    for (; ; ) {
      int lastSwapped = 0;
      for (int i = 0; i < max; i++) {
        int left = elements[i];
        int right = elements[i + 1];
        if (left > right) {
          elements[i + 1] = left;
          elements[i] = right;
          lastSwapped = i;
        }
      }
      if (lastSwapped == 0) break;
      max = lastSwapped;
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    int max = elements.length - 1;
    for (; ; ) {
      counters.incIterations();
      int lastSwapped = 0;
      for (int i = 0; i < max; i++) {
        counters.incIterations();

        int left = elements[i];
        int right = elements[i + 1];
        counters.addReads(2);
        counters.incComparisons();
        if (left > right) {
          elements[i + 1] = left;
          elements[i] = right;
          counters.addWrites(2);
          lastSwapped = i;
        }
      }
      if (lastSwapped == 0) break;
      max = lastSwapped;
    }
  }

}
