package eu.happycoders.sort.method;

public class HeapSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new HeapSort();
  }

}
