package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;

class MergeSort3Test extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new MergeSort3();
  }
}
