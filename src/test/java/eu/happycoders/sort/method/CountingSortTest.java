package eu.happycoders.sort.method;

public class CountingSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new CountingSort();
  }

}
