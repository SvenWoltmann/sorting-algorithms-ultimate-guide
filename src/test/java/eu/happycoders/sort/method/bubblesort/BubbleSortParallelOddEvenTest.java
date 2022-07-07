package eu.happycoders.sort.method.bubblesort;

import eu.happycoders.sort.method.*;

class BubbleSortParallelOddEvenTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new BubbleSortParallelOddEven();
  }
}
