package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksort.PivotStrategy;
import java.util.concurrent.ThreadLocalRandom;

class DualPivotQuicksortImprovedLeftRightTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new DualPivotQuicksortImproved(64, PivotStrategy.LEFT_RIGHT);
  }

  @Override
  protected int randomSize() {
    // Not more than 1000; going into recursion this deep, because partitioning
    // will always partition at the left or right side.
    return ThreadLocalRandom.current().nextInt(2, 1_000);
  }
}
