package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.*;

import java.util.concurrent.ThreadLocalRandom;

public class MergeSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new MergeSort();
  }

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }

}
