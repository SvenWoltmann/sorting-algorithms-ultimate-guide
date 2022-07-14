package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;

class RecursiveMsdRadixSortWithArraysAndCustomBase10Test extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new RecursiveMsdRadixSortWithArraysAndCustomBase(10);
  }
}
