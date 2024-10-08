package eu.happycoders.sort.demos.comparisons;

import eu.happycoders.sort.demos.Scorecard;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.ArrayUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Base class to directly compare two or more sort algorithms.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings({"PMD.SystemPrintln", "java:S106"})
public class DirectComparison {

  private static final int WARM_UPS = 15;
  private static final int ITERATIONS = 100;
  private static final int PRINT_RESULTS_ALL_X_ITERATIONS = 10;

  @SuppressWarnings("PMD.UseConcurrentHashMap") // Not accessed concurrently
  private final Map<String, Scorecard> scorecards = new HashMap<>();

  private int longestNameLength;

  void runTest(SortAlgorithm[] algorithms, int size) {
    longestNameLength = Scorecard.findLongestAlgorithmName(algorithms);

    int numAlgorithms = algorithms.length;

    System.out.println("----- Warming up -----");
    for (int i = 1; i <= WARM_UPS; i++) {
      System.out.printf("Iteration %d%n", i);
      int[] elements = ArrayUtils.createRandomArray(size);

      // The order in which we test affects which test is the fastest.
      // Maybe the JDK optimizes some parts specific to the input data!?
      // --> Shuffle the algorithms!
      int[] indices = getShuffledIndices(numAlgorithms);
      for (int j = 0; j < numAlgorithms; j++) {
        SortAlgorithm algorithm = algorithms[indices[j]];
        measure(algorithm, elements.clone());
        System.out.println();
      }
    }

    System.out.println("\n----- Measuring -----");
    for (int i = 1; i <= ITERATIONS; i++) {
      System.out.printf("Iteration %d%n", i);
      int[] elements = ArrayUtils.createRandomArray(size);

      int[] indices = getShuffledIndices(numAlgorithms);
      for (int j = 0; j < numAlgorithms; j++) {
        SortAlgorithm algorithm = algorithms[indices[j]];
        long time = measure(algorithm, elements.clone());
        boolean newRecord = scorecardForAlgorithm(algorithm).add(time);
        System.out.println(newRecord ? " <<< NEW RECORD :-)" : "");
      }

      if (i % PRINT_RESULTS_ALL_X_ITERATIONS == 0) {
        printResults(algorithms, i);
        System.out.println();
      }
    }
  }

  private static int[] getShuffledIndices(int numTestSets) {
    int[] indices = ArrayUtils.createSortedArray(numTestSets);
    ArrayUtils.shuffle(indices);
    return indices;
  }

  // In production code, we should never try to be smarter than the JVM; here we do try ;-)
  @SuppressWarnings({"PMD.DoNotCallGarbageCollectionExplicitly", "java:S1215"})
  private long measure(SortAlgorithm sortAlgorithm, int[] elements) {
    System.gc();

    long time = System.nanoTime();
    sortAlgorithm.sort(elements);
    time = System.nanoTime() - time;

    String format = "  %-" + longestNameLength + "s -> time = %,10.3f ms";
    System.out.printf(Locale.US, format, sortAlgorithm.getName(), time / 1_000_000.0);

    // Validate that's sorted (and make sure the sorting wasn't optimized away!)
    if (!ArrayUtils.isSorted(elements)) {
      throw new IllegalStateException("Array is not sorted.");
    }

    return time;
  }

  private Scorecard scorecardForAlgorithm(SortAlgorithm algorithm) {
    return scorecards.computeIfAbsent(algorithm.getName(), Scorecard::new);
  }

  private void printResults(SortAlgorithm[] algorithms, int iterations) {
    System.out.printf("%n----- Results after %d iterations-----%n", iterations);

    // 1. Find the fastest median
    int fastestAlgorithmIndex = 0;
    long fastestAlgorithmMedian = Long.MAX_VALUE;
    for (int i = 0; i < algorithms.length; i++) {
      long median = scorecardForAlgorithm(algorithms[i]).getMedian();
      if (median < fastestAlgorithmMedian) {
        fastestAlgorithmIndex = i;
        fastestAlgorithmMedian = median;
      }
    }

    // 2. Print them, showing which one has the fastest median
    for (int i = 0; i < algorithms.length; i++) {
      scorecardForAlgorithm(algorithms[i])
          .printResult(
              longestNameLength, i == fastestAlgorithmIndex ? "<<< FASTEST MEDIAN :-)" : null);
    }
  }
}
