package eu.happycoders.sort.method;

class SelectionSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new SelectionSort();
  }
}
