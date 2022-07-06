package eu.happycoders.sort;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.method.bubblesort.BubbleSortOpt1;
import eu.happycoders.sort.method.countingsort.CountingSort;
import eu.happycoders.sort.method.heapsort.BottomUpHeapsort;
import eu.happycoders.sort.method.heapsort.BottomUpHeapsortSlowComparisons;
import eu.happycoders.sort.method.heapsort.Heapsort;
import eu.happycoders.sort.method.heapsort.HeapsortSlowComparisons;
import eu.happycoders.sort.method.mergesort.MergeSort;
import eu.happycoders.sort.method.quicksort.*;
import eu.happycoders.sort.utils.ArrayUtils;
import eu.happycoders.sort.utils.Scorecard;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * Measures the performance of all sorting algorithms for various input sizes.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class UltimateTest {

  static final SortAlgorithm[] ALGORITHMS = new SortAlgorithm[]{
        new InsertionSort(),
        new SelectionSort(),
        new BubbleSortOpt1(),

        // Quicksort
        new QuicksortVariant1(PivotStrategy.RIGHT),
        new QuicksortVariant1(PivotStrategy.MIDDLE),
        new QuicksortVariant1(PivotStrategy.MEDIAN3),
        new QuicksortImproved(48, new QuicksortVariant1(PivotStrategy.MIDDLE)),
        new DualPivotQuicksort(DualPivotQuicksort.PivotStrategy.THIRDS),
        new DualPivotQuicksortImproved(64,
              DualPivotQuicksort.PivotStrategy.THIRDS),

        new MergeSort(),

        // Heapsort
        new Heapsort(),
        new BottomUpHeapsort(),
        new HeapsortSlowComparisons(),
        new BottomUpHeapsortSlowComparisons(),

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
    test(algorithm, InputOrder.RANDOM, ArrayUtils::createRandomArray,
          warmingUp);

    if (TEST_SORTED_INPUT) {
      test(algorithm, InputOrder.ASCENDING, ArrayUtils::createSortedArray,
            warmingUp);
      test(algorithm, InputOrder.DESCENDING, ArrayUtils::createReversedArray,
            warmingUp);
    }
  }

  private static void test(SortAlgorithm algorithm,
                           InputOrder inputOrder,
                           Function<Integer, int[]> arraySupplier,
                           boolean warmingUp) {
    System.out.printf("%n--- %s (order: %s) ---%n",
          algorithm.getName(), inputOrder);

    // Sort until sorting takes more than MAX_SORTING_TIME_SECS
    // Upper limit used by insertion sort on already sorted data
    for (int size = MIN_SORTING_SIZE;
         size <= MAX_SORTING_SIZE && algorithm.isSuitableForInputSize(size)
               && (!inputOrder.isSorted() || algorithm.isSuitableForSortedInput(size));
         size <<= 1) {
      long time = measureTime(algorithm, arraySupplier.apply(size));
      boolean newRecord = !warmingUp
            && scorecard(algorithm, inputOrder.toString(), size, true).add(time);

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

  private enum InputOrder {
    RANDOM(false),
    ASCENDING(true),
    DESCENDING(true);

    private final boolean sorted;

    InputOrder(boolean sorted) {
      this.sorted = sorted;
    }

    boolean isSorted() {
      return sorted;
    }

    @Override
    public String toString() {
      return name().toLowerCase();
    }
  }

}
