package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;

class RadixSortWithDynamicListsAndCustomBase2Test extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new RadixSortWithDynamicListsAndCustomBase(2);
  }
}
