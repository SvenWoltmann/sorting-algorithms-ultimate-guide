package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.bubblesort.*;
import java.util.List;

/**
 * Compares the regular Quicksort with the improved Quicksort for various thresholds at which the
 * algorithm switches from Quicksort to Insertion Sort.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareBubbleSorts extends DirectComparison {

  private static final int SIZE = 40_000; // ~500 ms for Bubble Sort

  public static void main(String[] args) {
    List<SortAlgorithm> algorithms =
        List.of(
            new BubbleSort(),
            new BubbleSortOpt1(),
            new BubbleSortOpt2(),
            new BubbleSortParallelOddEven(),
            new BubbleSortParallelDivideAndConquer());
    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }
}
