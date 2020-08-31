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
public class CountingSort implements SortAlgorithm {

  private static final int MAX_VALUE_TO_SORT = Integer.MAX_VALUE / 2;
  private static final int MIN_VALUE_TO_SORT = Integer.MIN_VALUE / 2;

  @Override
  public void sort(int[] elements) {
    Boundaries boundaries = findBoundaries(elements);
    int[] counts = new int[boundaries.max - boundaries.min + 1];

    // Phase 1: Count
    for (int i = 0; i < elements.length; i++) {
      counts[elements[i] - boundaries.min]++;
    }

    // Phase 2: Write results back
    int targetPos = 0;
    for (int i = 0; i < counts.length; i++) {
      for (int j = 0; j < counts[i]; j++) {
        elements[targetPos++] = i + boundaries.min;
      }
    }
  }

  private Boundaries findBoundaries(int[] elements) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < elements.length; i++) {
      int element = elements[i];
      if (element > MAX_VALUE_TO_SORT) {
        throw new IllegalArgumentException("Element " + element +
              " is greater than maximum " + MAX_VALUE_TO_SORT);
      }
      if (element < MIN_VALUE_TO_SORT) {
        throw new IllegalArgumentException("Element " + element +
              " is less than minimum " + MIN_VALUE_TO_SORT);
      }
      if (element > max) {
        max = element;
      }
      if (element < min) {
        min = element;
      }
    }
    return new Boundaries(min, max);
  }

  private static class Boundaries {
    private final int min;
    private final int max;

    public Boundaries(int min, int max) {
      this.min = min;
      this.max = max;
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    Boundaries boundaries = findBoundaries(elements);

    int length = elements.length;
    counters.addReads(length);
    counters.addIterations(length);
    counters.addComparisons(length);

    int[] counts = new int[boundaries.max - boundaries.min + 1];

    // Phase 1: Count
    counters.addIterations(length);
    counters.addReads(length); // read elements[i]
    counters.addReadsAndWrites(length); // inc counts[...]
    for (int i = 0; i < length; i++) {
      counts[elements[i] - boundaries.min]++;
    }

    // Phase 2: Write results back
    int targetPos = 0;
    counters.addIterations(counts.length); // outer
    counters.addIterations(length); // all inner combined
    counters.addReads(counts.length); // read counts[i]
    counters.addWrites(length); // write elements[targetPos++]
    for (int i = 0; i < counts.length; i++) {
      for (int j = 0; j < counts[i]; j++) {
        elements[targetPos++] = i + boundaries.min;
      }
    }
  }

}
