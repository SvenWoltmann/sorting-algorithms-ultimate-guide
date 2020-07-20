package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

import java.util.concurrent.ThreadLocalRandom;

public class QuicksortVariant2Median3PivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortVariant2(PivotStrategy.MEDIAN3);
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 500_000);
  }

}
