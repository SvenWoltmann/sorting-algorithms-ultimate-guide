package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.DualPivotQuicksort;
import eu.happycoders.sort.method.*;

import java.util.*;

/**
 * Compares Dual-Pivot Quicksort with the improved version for various
 * thresholds at which the algorithm switches from Quicksort to Insertion Sort.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareImprovedDualPivotQuickSort extends DirectComparison {

  private static final int SIZE = 5_555_555; // ~500 ms for Quicksort

  public static void main(String[] args) {
    List<SortAlgorithm> algorithms = new ArrayList<>();
    algorithms.add(new DualPivotQuicksort(DualPivotQuicksort.PivotStrategy.MIDDLES));
    for (int i = 2; i < 1 << 8; i <<= 1) {
      algorithms.add(new DualPivotQuicksortImproved(i,
            DualPivotQuicksort.PivotStrategy.MIDDLES));

      // From 16 elements on, add i+i/2 (-> 16, 24, 32, 48, 64, 96, 128, ...)
      if (i >= 16) {
        int i2 = i + i / 2;
        algorithms.add(new DualPivotQuicksortImproved(i2,
              DualPivotQuicksort.PivotStrategy.MIDDLES));
      }
    }
    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }

}
