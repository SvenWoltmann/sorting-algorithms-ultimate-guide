package eu.happycoders.sort.method;

import eu.happycoders.sort.method.Quicksort.PivotStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class QuicksortImprovedTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    int threshold = ThreadLocalRandom.current().nextInt(0, 100);
    PivotStrategy pivotStrategy = ThreadLocalRandom.current().nextBoolean() ?
          PivotStrategy.MIDDLE : PivotStrategy.MEDIAN3;
    return new QuicksortImproved(threshold, pivotStrategy);
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }

}
