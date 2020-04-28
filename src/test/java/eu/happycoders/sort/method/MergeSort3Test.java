package eu.happycoders.sort.method;

import java.util.concurrent.ThreadLocalRandom;

public class MergeSort3Test extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new MergeSort3();
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }

}
