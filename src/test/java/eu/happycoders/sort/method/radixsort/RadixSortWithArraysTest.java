package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;

class RadixSortWithArraysTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new RadixSortWithArrays();
  }
}
