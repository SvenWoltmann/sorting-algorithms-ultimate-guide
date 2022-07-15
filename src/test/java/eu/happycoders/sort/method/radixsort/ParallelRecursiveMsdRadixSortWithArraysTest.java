package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;

class ParallelRecursiveMsdRadixSortWithArraysTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new ParallelRecursiveMsdRadixSortWithArrays();
  }
}
