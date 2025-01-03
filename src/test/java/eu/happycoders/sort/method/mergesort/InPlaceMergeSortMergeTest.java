package eu.happycoders.sort.method.mergesort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import eu.happycoders.sort.utils.ArrayUtils;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class InPlaceMergeSortMergeTest {

  @Test
  void merge_twoSortedElements_merged() {
    testMerge(new int[] {8, 15});
  }

  @Test
  void merge_twoUnsortedElements_merged() {
    testMerge(new int[] {17, 4});
  }

  @RepeatedTest(100)
  void merge_twoRandomSectionsOnAList_merged() {
    int length = ThreadLocalRandom.current().nextInt(2, 20);
    int[] numbers = ArrayUtils.createRandomArray(length);
    testMerge(numbers);
  }

  private void testMerge(int[] numbers) {
    ThreadLocalRandom rand = ThreadLocalRandom.current();
    int length = numbers.length;
    int left = rand.nextInt(0, length / 2);
    int right = rand.nextInt(length / 2, length);
    int mid = left + (right - left) / 2;

    int[] arrayBefore = Arrays.copyOfRange(numbers, left, right + 1);
    Arrays.sort(numbers, left, mid + 1);
    Arrays.sort(numbers, mid + 1, right + 1);
    new InPlaceMergeSort().merge(numbers, left, mid + 1, right);
    int[] arrayAfter = Arrays.copyOfRange(numbers, left, right + 1);

    int[] expectedArray = arrayBefore.clone();
    Arrays.sort(expectedArray);
    assertArrayEquals(expectedArray, arrayAfter);
  }
}
