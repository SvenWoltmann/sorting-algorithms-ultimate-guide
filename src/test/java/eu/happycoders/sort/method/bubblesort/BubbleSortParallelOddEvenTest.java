package eu.happycoders.sort.method.bubblesort;

import eu.happycoders.sort.method.*;

public class BubbleSortParallelOddEvenTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new BubbleSortParallelOddEven();
  }

}
