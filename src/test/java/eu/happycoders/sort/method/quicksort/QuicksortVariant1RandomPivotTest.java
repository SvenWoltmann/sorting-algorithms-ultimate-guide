package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;

class QuicksortVariant1RandomPivotTest extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortVariant1(PivotStrategy.RANDOM);
  }
}
