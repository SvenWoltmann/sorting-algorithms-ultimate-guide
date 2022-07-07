package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;
import java.util.concurrent.ThreadLocalRandom;

class QuicksortImprovedVariant3Median3PivotTest extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    int threshold = ThreadLocalRandom.current().nextInt(0, 100);
    return new QuicksortImproved(threshold, new QuicksortVariant3(PivotStrategy.MEDIAN3));
  }
}
