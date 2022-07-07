package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;

class InPlaceMergeSortTest extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new InPlaceMergeSort();
  }
}
