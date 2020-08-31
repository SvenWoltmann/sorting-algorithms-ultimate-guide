package eu.happycoders.sort.method.countingsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;

/**
 * Counting Sort implementation for performance tests.
 *
 * <p>
 * For simplicity, this implementation allows only elements >= 0.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CountingSortSimple implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    int maxValue = findMax(elements);
    int[] counts = new int[maxValue + 1];

    // Phase 1: Count
    for (int i = 0; i < elements.length; i++) {
      counts[elements[i]]++;
    }

    // Phase 2: Write results back
    int targetPos = 0;
    for (int i = 0; i < counts.length; i++) {
      for (int j = 0; j < counts[i]; j++) {
        elements[targetPos++] = i;
      }
    }
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
    int maxValue = findMax(elements);

    int length = elements.length;
    counters.addReads(length);
    counters.addIterations(length);
    counters.addComparisons(length);

    int[] counts = new int[maxValue + 1];

    // Phase 1: Count
    counters.addIterations(length);
    counters.addReads(length); // read elements[i]
    counters.addReadsAndWrites(length); // inc counts[...]
    for (int i = 0; i < length; i++) {
      counts[elements[i]]++;
    }

    // Phase 2: Write results back
    Counters countersPhase2 = counters.getPhase2();
    int targetPos = 0;
    countersPhase2.addIterations(counts.length); // outer
    countersPhase2.addIterations(length); // all inner combined
    countersPhase2.addReads(counts.length); // read counts[i]
    countersPhase2.addWrites(length); // write elements[targetPos++]
    for (int i = 0; i < counts.length; i++) {
      for (int j = 0; j < counts[i]; j++) {
        elements[targetPos++] = i;
      }
    }
  }

}
