package eu.happycoders.sort.demos;

import eu.happycoders.sort.method.InsertionSort;
import eu.happycoders.sort.method.JavaArraysSort;
import eu.happycoders.sort.method.SelectionSort;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.bubblesort.BubbleSortOpt1;
import eu.happycoders.sort.method.countingsort.CountingSort;
import eu.happycoders.sort.method.heapsort.BottomUpHeapsort;
import eu.happycoders.sort.method.heapsort.BottomUpHeapsortSlowComparisons;
import eu.happycoders.sort.method.heapsort.Heapsort;
import eu.happycoders.sort.method.heapsort.HeapsortSlowComparisons;
import eu.happycoders.sort.method.mergesort.MergeSort;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksort;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksortImproved;
import eu.happycoders.sort.method.quicksort.PivotStrategy;
import eu.happycoders.sort.method.quicksort.QuicksortImproved;
import eu.happycoders.sort.method.quicksort.QuicksortVariant1;
import eu.happycoders.sort.method.radixsort.ParallelRadixSortWithArrays;
import eu.happycoders.sort.method.radixsort.ParallelRecursiveMsdRadixSortWithArrays;
import eu.happycoders.sort.method.radixsort.RadixSortWithArraysAndCustomBase;
import eu.happycoders.sort.method.radixsort.RadixSortWithCountingSortAndCustomBase;
import eu.happycoders.sort.method.radixsort.RadixSortWithDynamicListsAndCustomBase;
import eu.happycoders.sort.method.radixsort.RecursiveMsdRadixSortWithArraysAndCustomBase;
import eu.happycoders.sort.utils.ArrayUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * Measures the performance of all sorting algorithms for various input sizes.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings({"PMD.SystemPrintln", "java:S106"})
public class UltimateTest {

  static final SortAlgorithm[] ALGORITHMS = {
    new InsertionSort(),
    new SelectionSort(),
    new BubbleSortOpt1(),
    new CountingSort(),
    new JavaArraysSort(),

    // Quicksort
    new QuicksortVariant1(PivotStrategy.RIGHT),
    new QuicksortVariant1(PivotStrategy.MIDDLE),
    new QuicksortVariant1(PivotStrategy.MEDIAN3),
    new QuicksortImproved(48, new QuicksortVariant1(PivotStrategy.MIDDLE)),
    new DualPivotQuicksort(DualPivotQuicksort.PivotStrategy.THIRDS),
    new DualPivotQuicksortImproved(64, DualPivotQuicksort.PivotStrategy.THIRDS),
    new MergeSort(),

    // Heapsort
    new Heapsort(),
    new BottomUpHeapsort(),
    new HeapsortSlowComparisons(),
    new BottomUpHeapsortSlowComparisons(),

    // Radix Sort
    new RadixSortWithDynamicListsAndCustomBase(10),
    new RadixSortWithDynamicListsAndCustomBase(100),
    new RadixSortWithArraysAndCustomBase(10),
    new RadixSortWithArraysAndCustomBase(100),
    new RadixSortWithCountingSortAndCustomBase(10),
    new RadixSortWithCountingSortAndCustomBase(100),
    new RecursiveMsdRadixSortWithArraysAndCustomBase(10),
    new RecursiveMsdRadixSortWithArraysAndCustomBase(100),
    new ParallelRadixSortWithArrays(),
    new ParallelRecursiveMsdRadixSortWithArrays()
  };

  private static final int WARM_UPS = 2;

  private static final int MIN_SORTING_SIZE = 1 << 10;
  private static final int MAX_SORTING_SIZE = 1 << 29;

  // Stop when sorting takes longer than 20 seconds
  private static final int MAX_SORTING_TIME_SECS = 20;

  private static final boolean TEST_SORTED_INPUT = true;

  @SuppressWarnings("PMD.UseConcurrentHashMap") // Not accessed concurrently
  private final Map<String, Scorecard> scorecards = new HashMap<>();

  public static void main(String[] args) {
    new UltimateTest().run();
  }

  private void run() {
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

  private void test(SortAlgorithm algorithm, boolean warmingUp) {
    // Test with a random, a sorted, and a reversed (= sorted descending) array
    test(algorithm, InputOrder.RANDOM, ArrayUtils::createRandomArray, warmingUp);

    if (TEST_SORTED_INPUT) {
      test(algorithm, InputOrder.ASCENDING, ArrayUtils::createSortedArray, warmingUp);
      test(algorithm, InputOrder.DESCENDING, ArrayUtils::createReversedArray, warmingUp);
    }
  }

  private void test(
      SortAlgorithm algorithm,
      InputOrder inputOrder,
      IntFunction<int[]> arraySupplier,
      boolean warmingUp) {
    System.out.printf("%n--- %s (order: %s) ---%n", algorithm.getName(), inputOrder);

    // Sort until sorting takes more than MAX_SORTING_TIME_SECS
    // Upper limit used by insertion sort on already sorted data
    for (int size = MIN_SORTING_SIZE;
        size <= MAX_SORTING_SIZE
            && algorithm.isSuitableForInputSize(size)
            && (!inputOrder.isSorted() || algorithm.isSuitableForSortedInput(size));
        size <<= 1) {
      long time = measureTime(algorithm, arraySupplier.apply(size));
      boolean newRecord =
          !warmingUp && scorecard(algorithm, inputOrder.toString(), size, true).add(time);

      System.out.printf(
          Locale.US,
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

  // In production code, we should never try to be smarter than the JVM; here we do try ;-)
  @SuppressWarnings({"PMD.DoNotCallGarbageCollectionExplicitly", "java:S1215"})
  private long measureTime(SortAlgorithm algorithm, int[] elements) {
    System.gc();
    long time = System.nanoTime();
    algorithm.sort(elements);
    return System.nanoTime() - time;
  }

  private Scorecard scorecard(
      SortAlgorithm algorithm, String inputOrder, int size, boolean create) {
    String key = algorithm.getName() + "/" + inputOrder + "/" + size;
    return create ? scorecards.computeIfAbsent(key, Scorecard::new) : scorecards.get(key);
  }

  private void printResults(int iteration, SortAlgorithm algorithm, String inputOrder) {
    System.out.printf(
        "%n--- Results for iteration %d for: %s (order: %s) ---%n",
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
      return name().toLowerCase(Locale.US);
    }
  }
}
