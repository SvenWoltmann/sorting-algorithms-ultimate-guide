package eu.happycoders.sort.method;

class JavaArraysSortTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new JavaArraysSort();
  }
}
