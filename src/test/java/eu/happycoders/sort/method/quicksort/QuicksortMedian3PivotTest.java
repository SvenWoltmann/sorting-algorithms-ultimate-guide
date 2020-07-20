package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.method.quicksort.Quicksort.PivotStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class QuicksortMedian3PivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new Quicksort(PivotStrategy.MEDIAN3);
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }

}
