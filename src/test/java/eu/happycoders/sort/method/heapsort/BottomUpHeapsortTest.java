package eu.happycoders.sort.method.heapsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;

class BottomUpHeapsortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new BottomUpHeapsort();
  }
}
