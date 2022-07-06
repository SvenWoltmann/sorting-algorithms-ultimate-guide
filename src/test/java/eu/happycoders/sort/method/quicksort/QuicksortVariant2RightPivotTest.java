package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

public class QuicksortVariant2RightPivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortVariant2(PivotStrategy.RIGHT);
  }
}
