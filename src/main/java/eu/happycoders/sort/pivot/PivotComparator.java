package eu.happycoders.sort.pivot;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.method.quicksort.Quicksort;
import eu.happycoders.sort.utils.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Compares several pivot strategies: in how many samples will we have a
 * specific  distribution of elements (1.5:1, 2:1, 3:1) or better?
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class PivotComparator {

  private static final int ITERATIONS = 500_000;
  private static final int MIN_SIZE = 500;
  private static final int MAX_SIZE = 1_000;

  private static final Map<String, PivotScorecard> scorecards = new HashMap<>();

  private static int longestNameLength;

  public static void main(String[] args) {
    PartitioningAlgorithm[] algorithms = new PartitioningAlgorithm[]{
          new Quicksort(Quicksort.PivotStrategy.MIDDLE),
          new Quicksort(Quicksort.PivotStrategy.RANDOM),
          new Quicksort(Quicksort.PivotStrategy.RIGHT),
          new Quicksort(Quicksort.PivotStrategy.MEDIAN3)
    };
    runTest(algorithms);
  }

  private static void runTest(PartitioningAlgorithm[] algorithms) {
    longestNameLength = Scorecard.findLongestAlgorithmName(algorithms);

    int numAlgorithms = algorithms.length;
    ThreadLocalRandom rand = ThreadLocalRandom.current();

    for (int i = 1; i <= ITERATIONS; i++) {
      if (i % 10_000 == 0) {
        System.out.printf(Locale.US, "Iteration %,d%n", i);
      }

      // Check the same elements once with each algorithm
      int[] elements = ArrayUtils.createRandomArray(
            rand.nextInt(MIN_SIZE, MAX_SIZE));

      for (int j = 0; j < numAlgorithms; j++) {
        PartitioningAlgorithm algorithm = algorithms[j];
        double largerPartPercentage = partition(algorithm, elements.clone());
        scorecardForAlgorithm(algorithm).add(largerPartPercentage);
      }
    }

    System.out.printf(Locale.US,
          "%n---------- Results after %,d iterations ----------%n",
          ITERATIONS);
    for (int j = 0; j < numAlgorithms; j++) {
      PivotScorecard scorecard = scorecardForAlgorithm(algorithms[j]);
      scorecard.printResult(longestNameLength);
    }
  }

  /**
   * Partitions the elements with the given sort algorithm and calculates
   * the percentage of the larger partition.
   *
   * @param algorithm the algorithm
   * @param elements  the elements
   * @return the percentage of the larger partition; e.g., if we get 100 and 50
   * elements, the result is 66.7.
   */
  private static double partition(PartitioningAlgorithm algorithm,
                                  int[] elements) {
    int numElements = elements.length;
    int pivotPos = algorithm.partition(elements, 0, numElements - 1);

    int longerPartSize = pivotPos >= numElements / 2 ? pivotPos :
          numElements - 1 - pivotPos;

    return longerPartSize * 100.0 / (numElements - 1);
  }

  private static PivotScorecard scorecardForAlgorithm(SortAlgorithm algorithm) {
    return scorecards.computeIfAbsent(algorithm.getName(), PivotScorecard::new);
  }

}
