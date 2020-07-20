package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.utils.ArrayUtils;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortMergeTest {

  @Test
  public void merge_twoSortedElements_merged() {
    testMerge(new int[]{8}, new int[]{15});
  }

  @Test
  public void merge_twoUnsortedElements_merged() {
    testMerge(new int[]{17}, new int[]{4});
  }

  @RepeatedTest(100)
  public void merge_twoRandomSectionsOnAList_merged() {
    ThreadLocalRandom rand = ThreadLocalRandom.current();
    int[] leftArray = ArrayUtils.createRandomArray(rand.nextInt(1, 1000));
    Arrays.sort(leftArray);
    int[] rightArray = ArrayUtils.createRandomArray(rand.nextInt(1, 1000));
    Arrays.sort(rightArray);
    testMerge(leftArray, rightArray);
  }

  private void testMerge(int[] leftArray, int[] rightArray) {
    int[] merged = new MergeSort().merge(leftArray, rightArray);

    int[] expectedArray = new int[leftArray.length + rightArray.length];
    System.arraycopy(leftArray, 0, expectedArray, 0, leftArray.length);
    System.arraycopy(rightArray, 0, expectedArray, leftArray.length,
          rightArray.length);
    Arrays.sort(expectedArray);
    assertArrayEquals(expectedArray, merged);
  }

}
