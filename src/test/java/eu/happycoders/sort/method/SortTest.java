package eu.happycoders.sort.method;

import eu.happycoders.sort.utils.ArrayUtils;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class SortTest {

  @RepeatedTest(100)
  public void sort_randomNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createRandomArray);
  }

  @RepeatedTest(100)
  public void sort_sortedNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createSortedArray);
  }

  @RepeatedTest(100)
  public void sort_reverseNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createReversedArray);
  }

  private void sortAndTestIfSorted(Function<Integer, int[]> arraySupplier) {
    int[] numbers = arraySupplier.apply(randomSize());
    getSortAlgorithm().sort(numbers);
    assertIsSorted(numbers);
  }

  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 1_000);
  }

  static void assertIsSorted(int[] numbers) {
    for (int i = 0; i < numbers.length - 1; i++) {
      assertTrue(numbers[i] <= numbers[i + 1]);
    }
  }

  protected abstract SortAlgorithm getSortAlgorithm();

}
