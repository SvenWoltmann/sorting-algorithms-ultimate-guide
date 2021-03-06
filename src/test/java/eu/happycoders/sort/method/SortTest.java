package eu.happycoders.sort.method;

import eu.happycoders.sort.utils.ArrayUtils;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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

  protected void sortAndTestIfSorted(Function<Integer, int[]> arraySupplier) {
    int[] numbers = arraySupplier.apply(randomSize());

    int[] numbersCopy = numbers.clone();
    Arrays.sort(numbersCopy);

    getSortAlgorithm().sort(numbers);

    assertArrayEquals(numbersCopy, numbers);
  }

  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 1_000);
  }

  protected abstract SortAlgorithm getSortAlgorithm();

}
