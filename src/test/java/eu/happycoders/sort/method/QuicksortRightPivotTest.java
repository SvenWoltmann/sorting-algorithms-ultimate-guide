package eu.happycoders.sort.method;

import eu.happycoders.sort.method.Quicksort.PivotStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class QuicksortRightPivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new Quicksort(PivotStrategy.RIGHT);
  }

  @Override
  protected int randomSize() {
    // Not more than 1000; going into recursion this deep, because partitioning
    // will always partition at the left or right side.
    return ThreadLocalRandom.current().nextInt(2, 1000);
  }

}
