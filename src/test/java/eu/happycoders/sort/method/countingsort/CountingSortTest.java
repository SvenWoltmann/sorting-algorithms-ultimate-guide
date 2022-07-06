package eu.happycoders.sort.method.countingsort;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.SortTest;
import eu.happycoders.sort.utils.ArrayUtils;
import org.junit.jupiter.api.RepeatedTest;

public class CountingSortTest extends SortTest {

  @RepeatedTest(100)
  public void sort_randomNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createRandomArrayIncludingNegatives);
  }

  @RepeatedTest(100)
  public void sort_sortedNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createSortedArrayIncludingNegatives);
  }

  @RepeatedTest(100)
  public void sort_reverseNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createReversedArrayIncludingNegatives);
  }

  @Override
  protected SortAlgorithm getSortAlgorithm() {
    return new CountingSort();
  }
}
