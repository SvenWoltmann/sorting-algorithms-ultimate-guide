package eu.happycoders.sort.method;

/**
 * Counting Sort implementation for performance tests.
 *
 * <p>
 * For simplicity, this implementation allows only elements >= 0.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CountingSort implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    int maxValue = findMax(elements);
    int[] counts = new int[maxValue + 1];

    // Count
    for (int i = 0; i < elements.length; i++) {
      counts[elements[i]]++;
    }

    // Write results back
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
      if (elements[i] > max) {
        max = elements[i];
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

    // Count
    counters.addIterations(length);
    counters.addReads(length); // read elements[i]
    counters.addReadsAndWrites(length); // inc counts[...]
    for (int i = 0; i < length; i++) {
      counts[elements[i]]++;
    }

    // Write results back
    int targetPos = 0;
    counters.addIterations(counts.length); // outer
    counters.addIterations(length); // all inner combined
    counters.addReads(counts.length); // read counts[i]
    counters.addWrites(length); // write elements[targetPos++]
    for (int i = 0; i < counts.length; i++) {
      for (int j = 0; j < counts[i]; j++) {
        elements[targetPos++] = i;
      }
    }
  }

}
