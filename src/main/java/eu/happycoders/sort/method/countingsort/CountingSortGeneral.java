package eu.happycoders.sort.method.countingsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;

/**
 * General Counting Sort implementation.
 *
 * <p>
 * For simplicity, this implementation allows only elements >= 0.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CountingSortGeneral implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    int maxValue = findMax(elements);
    int[] counts = new int[maxValue + 1];

    // Phase 1: Count
    for (int i = 0; i < elements.length; i++) {
      counts[elements[i]]++;
    }

    // Phase 2: Aggregate
    for (int i = 1; i <= maxValue; i++) {
      counts[i] += counts[i - 1];
    }

    // Phase 3: Write to target array
    int[] target = new int[elements.length];
    for (int i = elements.length - 1; i >= 0; i--) {
      int element = elements[i];
      target[--counts[element]] = element;
    }

    // Copy target back to input array
    System.arraycopy(target, 0, elements, 0, elements.length);
  }

  private int findMax(int[] elements) {
    int max = 0;
    for (int i = 0; i < elements.length; i++) {
      int element = elements[i];
      if (element < 0) {
        throw new IllegalArgumentException(
              "This implementation does not support negative values.");
      }
      if (element > max) {
        max = element;
      }
    }
    return max;
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    throw new IllegalStateException("Not implemented");
  }

}
