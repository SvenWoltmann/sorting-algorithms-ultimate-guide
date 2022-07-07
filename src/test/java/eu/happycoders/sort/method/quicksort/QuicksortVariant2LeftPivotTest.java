package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

class QuicksortVariant2LeftPivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortVariant2(PivotStrategy.LEFT);
  }
}
