package eu.happycoders.sort.method.countingsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;

class CountingSortGeneralTest extends SortTest {

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new CountingSortGeneral();
  }
}
