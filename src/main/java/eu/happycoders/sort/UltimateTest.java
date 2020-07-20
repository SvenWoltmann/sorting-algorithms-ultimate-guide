package eu.happycoders.sort;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.method.bubblesort.BubbleSortOpt1;
import eu.happycoders.sort.method.mergesort.MergeSort;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksort;
import eu.happycoders.sort.method.quicksort.*;
import eu.happycoders.sort.utils.*;

import java.util.*;
import java.util.function.Function;

/**
 * Measures the performance of all sorting algorithms for various input sizes.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class UltimateTest {

  static final SortAlgorithm[] ALGORITHMS = {
        new InsertionSort(),
        new SelectionSort(),
        new BubbleSortOpt1(),
        new Quicksort(Quicksort.PivotStrategy.MIDDLE),
        new QuicksortImproved(48, Quicksort.PivotStrategy.MIDDLE),
        new DualPivotQuicksort(DualPivotQuicksort.PivotStrategy.MIDDLES),
        new DualPivotQuicksortImproved(48,
              DualPivotQuicksort.PivotStrategy.MIDDLES),
        new MergeSort(),
        new HeapSort(),
        new CountingSort(),
        new JavaArraysSort()
  };

  private static final int WARM_UPS = 2;

  private static final int MIN_SORTING_SIZE = 1 << 10;
  private static final int MAX_SORTING_SIZE = 1 << 29;

  // Stop when sorting takes longer than 20 seconds
  private static final int MAX_SORTING_TIME_SECS = 20;

  private static final boolean TEST_SORTED_INPUT = true;

  private static final Map<String, Scorecard> scorecards = new HashMap<>();

  public static void main(String[] args) {
    for (int i = 1; i <= WARM_UPS; i++) {
      System.out.printf("%n===== Warm up %d of %d =====%n", i, WARM_UPS);
      for (SortAlgorithm algorithm : ALGORITHMS) {
        test(algorithm, true);
      }
    }

    for (int i = 1; ; i++) {
      System.out.printf("%n===== Iteration %d =====%n", i);
      for (SortAlgorithm algorithm : ALGORITHMS) {
        test(algorithm, false);
      }
      System.out.printf("%n===== Results for iteration %d =====%n", i);
      for (SortAlgorithm algorithm : ALGORITHMS) {
        printResults(i, algorithm, "random");
        if (TEST_SORTED_INPUT) {
          printResults(i, algorithm, "ascending");
          printResults(i, algorithm, "descending");
        }
      }
    }
  }

  private static void test(SortAlgorithm algorithm, boolean warmingUp) {
    // Test with a random, a sorted, and a reversed (= sorted descending) array
    test(algorithm, "random", ArrayUtils::createRandomArray, warmingUp);

    // Quicksort would go n x into recursion here...
    if (TEST_SORTED_INPUT && algorithm.isSuitableForSortedInput()) {
      test(algorithm, "ascending", ArrayUtils::createSortedArray, warmingUp);
      test(algorithm, "descending", ArrayUtils::createReversedArray,
            warmingUp);
    }
  }

  private static void test(SortAlgorithm algorithm,
                           String inputOrder,
                           Function<Integer, int[]> arraySupplier,
                           boolean warmingUp) {
    System.out.printf("%n--- %s (order: %s) ---%n",
          algorithm.getName(), inputOrder);

    // Sort until sorting takes more than MAX_SORTING_TIME_SECS
    // Upper limit used by insertion sort on already sorted data
    for (int size = MIN_SORTING_SIZE;
         size <= MAX_SORTING_SIZE && algorithm.isSuitableForInputSize(size);
         size <<= 1) {
      long time = measureTime(algorithm, arraySupplier.apply(size));
      boolean newRecord = !warmingUp
            && scorecard(algorithm, inputOrder, size, true).add(time);

      System.out.printf(Locale.US,
            "%s (order: %s): size = %,11d  -->  time = %,10.3f ms %s%n",
            algorithm.getName(),
            inputOrder,
            size,
            time / 1_000_000.0,
            newRecord ? "<<< NEW RECORD :-)" : "");

      // Stop after specified time
      if (time > MAX_SORTING_TIME_SECS * 1_000_000_000L) {
        break;
      }
    }
  }

  private static long measureTime(SortAlgorithm algorithm, int[] elements) {
    System.gc();
    long time = System.nanoTime();
    algorithm.sort(elements);
    return System.nanoTime() - time;
  }

  private static Scorecard scorecard(SortAlgorithm algorithm,
                                     String inputOrder, int size,
                                     boolean create) {
    String key = algorithm.getName() + "/" + inputOrder + "/" + size;
    return create
          ? scorecards.computeIfAbsent(key, Scorecard::new)
          : scorecards.get(key);
  }

  private static void printResults(int iteration, SortAlgorithm algorithm,
                                   String inputOrder) {
    System.out.printf("%n--- Results for iteration %d for: %s (order: %s) ---%n",
          iteration, algorithm.getName(), inputOrder);

    int longestNameLength = 0;
    for (int size = MIN_SORTING_SIZE;
         size <= MAX_SORTING_SIZE && algorithm.isSuitableForInputSize(size);
         size <<= 1) {
      Scorecard scorecard = scorecard(algorithm, inputOrder, size, false);
      if (scorecard != null) {
        int nameLength = scorecard.getName().length();
        if (nameLength > longestNameLength) {
          longestNameLength = nameLength;
        }
      }
    }

    for (int size = MIN_SORTING_SIZE;
         size <= MAX_SORTING_SIZE && algorithm.isSuitableForInputSize(size);
         size <<= 1) {
      Scorecard scorecard = scorecard(algorithm, inputOrder, size, false);
      if (scorecard != null) {
        scorecard.printResult(longestNameLength, "");
      }
    }
  }

}
