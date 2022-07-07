package eu.happycoders.sort.method;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import eu.happycoders.sort.utils.ArrayUtils;
import eu.happycoders.sort.utils.NotImplementedException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public abstract class SortTest {

  @RepeatedTest(100)
  void sort_randomNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createRandomArray);
  }

  @RepeatedTest(100)
  void sort_sortedNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createSortedArray);
  }

  @RepeatedTest(100)
  void sort_reverseNumbers_sorted() {
    sortAndTestIfSorted(ArrayUtils::createReversedArray);
  }

  protected void sortAndTestIfSorted(Function<Integer, int[]> arraySupplier) {
    int[] numbers = arraySupplier.apply(randomSize());

    int[] numbersCopy = numbers.clone();
    Arrays.sort(numbersCopy);

    getSortAlgorithm().sort(numbers);

    assertArrayEquals(numbersCopy, numbers);
  }

  @Test
  void sortWithCounter_randomNumbers_eitherSortedOrNotImplementedException() {
    int[] numbers = ArrayUtils.createRandomArray(randomSize());

    int[] numbersCopy = numbers.clone();
    Arrays.sort(numbersCopy);

    SortAlgorithm sortAlgorithm = getSortAlgorithm();
    Counters counters = new Counters();
    if (sortAlgorithm.supportsCounting()) {
      sortAlgorithm.sortWithCounters(numbers, counters);
      assertArrayEquals(numbersCopy, numbers);
    } else {
      assertThrows(
          NotImplementedException.class, () -> sortAlgorithm.sortWithCounters(numbers, counters));
    }
  }

  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 1_000);
  }

  protected abstract SortAlgorithm getSortAlgorithm();
}
