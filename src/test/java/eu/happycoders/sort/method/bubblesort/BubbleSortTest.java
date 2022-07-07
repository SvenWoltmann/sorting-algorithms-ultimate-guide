package eu.happycoders.sort.method.bubblesort;

import eu.happycoders.sort.method.*;

class BubbleSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new BubbleSort();
  }
}
