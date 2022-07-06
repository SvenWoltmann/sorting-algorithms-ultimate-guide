package eu.happycoders.sort.method;

public class SelectionSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new SelectionSort();
  }
}
