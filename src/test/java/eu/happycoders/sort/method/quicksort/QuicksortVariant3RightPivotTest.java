package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

class QuicksortVariant3RightPivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortVariant3(PivotStrategy.RIGHT);
  }
}
