package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;

class QuicksortVariant1Median3PivotTest extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new QuicksortVariant1(PivotStrategy.MEDIAN3);
  }
}
