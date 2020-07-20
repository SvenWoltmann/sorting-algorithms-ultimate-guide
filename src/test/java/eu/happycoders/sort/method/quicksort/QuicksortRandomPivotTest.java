package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.method.quicksort.Quicksort.PivotStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class QuicksortRandomPivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new Quicksort(PivotStrategy.RANDOM);
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }

}
