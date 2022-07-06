package eu.happycoders.sort.method;

public class InsertionSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new InsertionSort();
  }
}
