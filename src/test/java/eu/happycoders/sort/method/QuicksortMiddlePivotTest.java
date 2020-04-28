package eu.happycoders.sort.method;

import eu.happycoders.sort.method.Quicksort.PivotStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class QuicksortMiddlePivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new Quicksort(PivotStrategy.MIDDLE);
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }

}
