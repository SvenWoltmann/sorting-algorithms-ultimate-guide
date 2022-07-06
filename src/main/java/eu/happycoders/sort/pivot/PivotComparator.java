package eu.happycoders.sort.pivot;

import eu.happycoders.sort.method.PartitioningAlgorithm;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.quicksort.PivotStrategy;
import eu.happycoders.sort.method.quicksort.QuicksortVariant1;
import eu.happycoders.sort.utils.ArrayUtils;
import eu.happycoders.sort.utils.Scorecard;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Compares several pivot strategies: in how many samples will we have a specific distribution of
 * elements (1.5:1, 2:1, 3:1) or better?
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
// Ignore "weak cryptography" warning - we're just sorting random numbers :-)
@SuppressWarnings("java:S2245")
public class PivotComparator {

  private static final int ITERATIONS = 500_000;
  private static final int MIN_SIZE = 500;
  private static final int MAX_SIZE = 1_000;

  private final Map<String, PivotScorecard> scorecards = new HashMap<>();

  private int longestNameLength;

  public static void main(String[] args) {
    new PivotComparator().run();
  }

  private void run() {
    PartitioningAlgorithm[] algorithms =
        new PartitioningAlgorithm[] {
          new QuicksortVariant1(PivotStrategy.MIDDLE),
          new QuicksortVariant1(PivotStrategy.RANDOM),
          new QuicksortVariant1(PivotStrategy.RIGHT),
          new QuicksortVariant1(PivotStrategy.MEDIAN3)
        };

    runTest(algorithms);
  }

  @SuppressWarnings({"PMD.SystemPrintln", "java:S106"})
  private void runTest(PartitioningAlgorithm[] algorithms) {
    longestNameLength = Scorecard.findLongestAlgorithmName(algorithms);

    int numAlgorithms = algorithms.length;
    ThreadLocalRandom rand = ThreadLocalRandom.current();

    for (int i = 1; i <= ITERATIONS; i++) {
      if (i % 10_000 == 0) {
        System.out.printf(Locale.US, "Iteration %,d%n", i);
      }

      // Check the same elements once with each algorithm
      int[] elements = ArrayUtils.createRandomArray(rand.nextInt(MIN_SIZE, MAX_SIZE));

      for (int j = 0; j < numAlgorithms; j++) {
        PartitioningAlgorithm algorithm = algorithms[j];
        double largerPartPercentage = partition(algorithm, elements.clone());
        scorecardForAlgorithm(algorithm).add(largerPartPercentage);
      }
    }

    System.out.printf(
        Locale.US, "%n---------- Results after %,d iterations ----------%n", ITERATIONS);
    for (int j = 0; j < numAlgorithms; j++) {
      PivotScorecard scorecard = scorecardForAlgorithm(algorithms[j]);
      scorecard.printResult(longestNameLength);
    }
  }

  /**
   * Partitions the elements with the given sort algorithm and calculates the percentage of the
   * larger partition.
   *
   * @param algorithm the algorithm
   * @param elements the elements
   * @return the percentage of the larger partition; e.g., if we get 100 and 50 elements, the result
   *     is 66.7.
   */
  private double partition(PartitioningAlgorithm algorithm, int[] elements) {
    int numElements = elements.length;
    int pivotPos = algorithm.partition(elements, 0, numElements - 1);

    int longerPartSize = pivotPos >= numElements / 2 ? pivotPos : numElements - 1 - pivotPos;

    return longerPartSize * 100.0 / (numElements - 1);
  }

  private PivotScorecard scorecardForAlgorithm(SortAlgorithm algorithm) {
    return scorecards.computeIfAbsent(algorithm.getName(), PivotScorecard::new);
  }
}
