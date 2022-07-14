package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;

class RecursiveMsdRadixSortWithArraysAndCustomBase8Test extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new RecursiveMsdRadixSortWithArraysAndCustomBase(8);
  }
}
