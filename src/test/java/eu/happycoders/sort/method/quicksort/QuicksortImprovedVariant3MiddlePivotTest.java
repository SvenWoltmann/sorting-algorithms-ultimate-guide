package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;

import java.util.concurrent.ThreadLocalRandom;

public class QuicksortImprovedVariant3MiddlePivotTest extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    int threshold = ThreadLocalRandom.current().nextInt(0, 100);
    return new QuicksortImproved(threshold,
          new QuicksortVariant3(PivotStrategy.MIDDLE));
  }

}
