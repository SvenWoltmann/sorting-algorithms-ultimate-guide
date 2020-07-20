package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksort;
import eu.happycoders.sort.method.quicksort.*;

import java.util.*;

/**
 * Compares Dual-Pivot Quicksort with the improved version for various
 * thresholds at which the algorithm switches from Quicksort to Insertion Sort.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareImprovedDualPivotQuicksort extends DirectComparison {

  private static final int SIZE = 5_555_555; // ~500 ms for Quicksort

  public static void main(String[] args) {
    List<SortAlgorithm> algorithms = new ArrayList<>();
    algorithms.add(new DualPivotQuicksort(DualPivotQuicksort.PivotStrategy.THIRDS));
    for (int threshold = 2; threshold < 1 << 8; threshold <<= 1) {
      algorithms.add(new DualPivotQuicksortImproved(threshold,
            DualPivotQuicksort.PivotStrategy.THIRDS));

      // From 16 elements on, add threshold + threshold / 2
      // (... 16, 24, 32, 48, 64, 96, 128, 196)
      //          ^^      ^^      ^^       ^^^
      if (threshold >= 16) {
        algorithms.add(new DualPivotQuicksortImproved(threshold + threshold / 2,
              DualPivotQuicksort.PivotStrategy.THIRDS));
      }
    }
    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }

}
