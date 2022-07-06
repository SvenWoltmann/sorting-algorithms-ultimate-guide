package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksort.PivotStrategy;
import java.util.concurrent.ThreadLocalRandom;

public class DualPivotQuicksortLeftRightTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new DualPivotQuicksort(PivotStrategy.LEFT_RIGHT);
  }

  @Override
  protected int randomSize() {
    // Not more than 1000; going into recursion this deep, because partitioning
    // will always partition at the left or right side.
    return ThreadLocalRandom.current().nextInt(2, 1_000);
  }
}
