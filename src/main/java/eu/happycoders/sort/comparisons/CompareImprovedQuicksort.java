package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.quicksort.*;
import java.util.*;

/**
 * Compares the regular Quicksort with the improved Quicksort for various thresholds at which the
 * algorithm switches from Quicksort to Insertion Sort.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareImprovedQuicksort extends DirectComparison {

  private static final int SIZE = 5_555_555; // ~500 ms for Quicksort

  public static void main(String[] args) {
    List<SortAlgorithm> algorithms = new ArrayList<>();
    algorithms.add(new QuicksortVariant1(PivotStrategy.MIDDLE));
    algorithms.add(new QuicksortVariant1(PivotStrategy.MEDIAN3));
    algorithms.add(new QuicksortVariant2(PivotStrategy.MIDDLE));
    algorithms.add(new QuicksortVariant2(PivotStrategy.MEDIAN3));
    algorithms.add(new QuicksortVariant3(PivotStrategy.MIDDLE));
    algorithms.add(new QuicksortVariant3(PivotStrategy.MEDIAN3));

    for (int threshold = 2; threshold < 1 << 8; threshold <<= 1) {
      addAllVariantsForThreshold(algorithms, threshold);

      // From 16 elements on, add threshold + threshold / 2
      // (... 16, 24, 32, 48, 64, 96, 128, 196)
      //          ^^      ^^      ^^       ^^^
      if (threshold >= 16) {
        addAllVariantsForThreshold(algorithms, threshold + threshold / 2);
      }
    }

    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }

  private static void addAllVariantsForThreshold(List<SortAlgorithm> algorithms, int threshold) {
    // Variant 1
    algorithms.add(new QuicksortImproved(threshold, new QuicksortVariant1(PivotStrategy.MIDDLE)));
    algorithms.add(new QuicksortImproved(threshold, new QuicksortVariant1(PivotStrategy.MEDIAN3)));

    // Variant 2
    algorithms.add(new QuicksortImproved(threshold, new QuicksortVariant2(PivotStrategy.MIDDLE)));
    algorithms.add(new QuicksortImproved(threshold, new QuicksortVariant2(PivotStrategy.MEDIAN3)));

    // Variant 3
    algorithms.add(new QuicksortImproved(threshold, new QuicksortVariant3(PivotStrategy.MIDDLE)));
    algorithms.add(new QuicksortImproved(threshold, new QuicksortVariant3(PivotStrategy.MEDIAN3)));
  }
}
