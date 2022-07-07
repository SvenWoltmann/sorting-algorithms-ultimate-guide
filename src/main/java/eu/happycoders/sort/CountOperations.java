package eu.happycoders.sort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.ArrayUtils;
import java.util.Locale;
import java.util.function.IntFunction;

/**
 * Measures the performance of all sorting algorithms for various input sizes.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CountOperations {

  private static final int MIN_SORTING_SIZE = 1 << 3;
  private static final int MAX_SORTING_SIZE = 1 << 26;

  // Stop when counting takes longer than 20 seconds
  private static final int MAX_COUNTING_TIME_SECS = 20;

  public static void main(String[] args) {
    new CountOperations().run();
  }

  private void run() {
    for (SortAlgorithm algorithm : UltimateTest.ALGORITHMS) {
      if (algorithm.supportsCounting()) {
        countOps(algorithm);
      }
    }
  }

  private void countOps(SortAlgorithm algorithm) {
    // Test with a random, a sorted, and a reversed (= sorted descending) array
    countOps(algorithm, false, "random", ArrayUtils::createRandomArray);
    countOps(algorithm, true, "ascending", ArrayUtils::createSortedArray);
    countOps(algorithm, true, "descending", ArrayUtils::createReversedArray);
  }

  @SuppressWarnings({"PMD.SystemPrintln", "java:S106"})
  private void countOps(
      SortAlgorithm algorithm,
      boolean sorted,
      String inputOrder,
      IntFunction<int[]> arraySupplier) {
    System.out.printf("%n--- %s (order: %s) ---%n", algorithm.getName(), inputOrder);

    // Sort until sorting takes more than MAX_SORTING_TIME_SECS
    // Upper limit used by insertion sort on already sorted data
    for (int size = MIN_SORTING_SIZE;
        size <= MAX_SORTING_SIZE
            && algorithm.isSuitableForInputSize(size)
            && (!sorted || algorithm.isSuitableForSortedInput(size));
        size <<= 1) {
      long time = System.currentTimeMillis();
      Counters counters = countOps(algorithm, arraySupplier.apply(size));
      time = System.currentTimeMillis() - time;

      System.out.printf(
          Locale.US,
          "%s (order: %s): size = %,11d  -->  %s%n",
          algorithm.getName(),
          inputOrder,
          size,
          counters);

      // Stop after specified time
      if (time > MAX_COUNTING_TIME_SECS * 1_000L) {
        break;
      }
    }
  }

  private Counters countOps(SortAlgorithm algorithm, int[] elements) {
    Counters counters = new Counters();
    algorithm.sortWithCounters(elements, counters);
    return counters;
  }
}
