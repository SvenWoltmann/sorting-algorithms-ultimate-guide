package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

class QuicksortSimpleTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortSimple();
  }
}
