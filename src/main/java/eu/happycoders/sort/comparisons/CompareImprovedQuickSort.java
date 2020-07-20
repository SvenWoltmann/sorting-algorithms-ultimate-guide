package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.quicksort.*;
import eu.happycoders.sort.method.quicksort.Quicksort.PivotStrategy;

import java.util.*;

/**
 * Compares the regular Quicksort with the improved Quicksort for various
 * thresholds at which the algorithm switches from Quicksort to Insertion Sort.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareImprovedQuickSort extends DirectComparison {

  private static final int SIZE = 5_555_555; // ~500 ms for Quicksort

  public static void main(String[] args) {
    List<SortAlgorithm> algorithms = new ArrayList<>();
    algorithms.add(new Quicksort(PivotStrategy.MIDDLE));
    algorithms.add(new Quicksort(PivotStrategy.MEDIAN3));

    for (int i = 2; i < 1 << 8; i <<= 1) {
      algorithms.add(new QuicksortImproved(i, PivotStrategy.MIDDLE));
      algorithms.add(new QuicksortImproved(i, PivotStrategy.MEDIAN3));

      // From 16 elements on, add i+i/2 (-> 16, 24, 32, 48, 64, 96, 128, ...)
      if (i >= 16) {
        int i2 = i + i / 2;
        algorithms.add(new QuicksortImproved(i2, PivotStrategy.MIDDLE));
        algorithms.add(new QuicksortImproved(i2, PivotStrategy.MEDIAN3));
      }
    }

    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }

}
