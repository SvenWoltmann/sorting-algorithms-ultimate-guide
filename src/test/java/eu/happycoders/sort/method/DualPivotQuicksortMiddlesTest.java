package eu.happycoders.sort.method;

import eu.happycoders.sort.method.DualPivotQuicksort.PivotStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class DualPivotQuicksortMiddlesTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new DualPivotQuicksort(PivotStrategy.MIDDLES);
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }

}
