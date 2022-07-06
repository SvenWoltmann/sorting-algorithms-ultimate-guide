package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

public class QuicksortVariant1RightPivotTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortVariant1(PivotStrategy.RIGHT);
  }
}
