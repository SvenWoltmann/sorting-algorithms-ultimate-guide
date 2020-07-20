package eu.happycoders.sort.method.bubblesort;

import eu.happycoders.sort.method.*;

/**
 * Bubble Sort implementation for performance tests.
 *
 * <p>
 * Optimized: in the n-th iteration, the n-th largest element is put into
 * place, so we can ignore the last n-1 elements.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BubbleSortOpt1 implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    for (int max = elements.length - 1; max > 0; max--) {
      boolean swapped = false;
      for (int i = 0; i < max; i++) {
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
    for (int max = elements.length - 1; max > 0; max--) {
      counters.incIterations();

      boolean swapped = false;
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
          swapped = true;
        }
      }
      if (!swapped) break;
    }
  }

}
