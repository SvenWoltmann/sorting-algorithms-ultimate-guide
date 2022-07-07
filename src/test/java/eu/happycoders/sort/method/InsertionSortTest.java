package eu.happycoders.sort.method;

class InsertionSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new InsertionSort();
  }
}
