package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.method.Quicksort.PivotStrategy;

import java.util.*;

/**
 * Compares the regular Quicksort with the improved Quicksort for various
 * thresholds at which the algorithm switches from Quicksort to Insertion Sort.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareQuickSorts extends DirectComparison {

  private static final int SIZE = 5_555_555; // ~500 ms for Quicksort

  public static void main(String[] args) {
    List<SortAlgorithm> algorithms = new ArrayList<>();
    algorithms.add(new Quicksort(PivotStrategy.MIDDLE));
    algorithms.add(new Quicksort(PivotStrategy.MEDIAN3));
    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }

}
