package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

public class QuicksortSimpleTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortSimple();
  }
}
