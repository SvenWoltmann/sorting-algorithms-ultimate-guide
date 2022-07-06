package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTestLargeArray;

public class NaturalMergeSortTest extends SortTestLargeArray {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new NaturalMergeSort();
  }
}
