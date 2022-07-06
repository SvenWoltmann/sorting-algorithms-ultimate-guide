package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksort.PivotStrategy;

public class DualPivotQuicksortThirdsTest extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new DualPivotQuicksort(PivotStrategy.THIRDS);
  }
}
