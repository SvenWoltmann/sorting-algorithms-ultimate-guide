package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;

/**
 * Merge sort implementation for performance tests.
 *
 * <p>
 * This implementation divides directly on the input array and creates new
 * arrays only in the merge phase, passing the new arrays up the call chain
 * and merging them; finally copying the resulting array back to the input
 * array.
 *
 * <p>
 * Additional space complexity is <strong>O(n)</strong>.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class MergeSort implements SortAlgorithm {

  // Otherwise we run out of heap
  private static final int MAX_INPUT_SIZE = 1 << 28;

  @Override
  public void sort(int[] elements) {
    int length = elements.length;
    int[] sorted = mergeSort(elements, 0, length - 1);
    System.arraycopy(sorted, 0, elements, 0, length);
  }

  private int[] mergeSort(int[] elements, int left, int right) {
    // End of recursion reached?
    if (left == right) return new int[]{elements[left]};

    int middle = left + (right - left) / 2;
    int[] leftArray = mergeSort(elements, left, middle);
    int[] rightArray = mergeSort(elements, middle + 1, right);
    return merge(leftArray, rightArray);
  }

  int[] merge(int[] leftArray, int[] rightArray) {
    int leftLen = leftArray.length;
    int rightLen = rightArray.length;

    int[] target = new int[leftLen + rightLen];
    int targetPos = 0;
    int leftPos = 0;
    int rightPos = 0;

    // As long as both arrays contain elements...
    while (leftPos < leftLen && rightPos < rightLen) {
      // Which one is smaller?
      int leftValue = leftArray[leftPos];
      int rightValue = rightArray[rightPos];
      if (leftValue <= rightValue) {
        target[targetPos++] = leftValue;
        leftPos++;
      } else {
        target[targetPos++] = rightValue;
        rightPos++;
      }
    }
    // Copy the rest
    while (leftPos < leftLen) {
      target[targetPos++] = leftArray[leftPos++];
    }
    while (rightPos < rightLen) {
      target[targetPos++] = rightArray[rightPos++];
    }
    return target;
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    int length = elements.length;
    int[] sorted = mergeSort(elements, 0, length - 1, counters);
    System.arraycopy(sorted, 0, elements, 0, length);
    counters.addReadsAndWrites(length);
  }

  private int[] mergeSort(int[] elements, int left, int right,
                          Counters counters) {
    // End of recursion reached?
    if (left == right) return new int[]{elements[left]};

    int middle = left + (right - left) / 2;
    int[] leftArray = mergeSort(elements, left, middle, counters);
    int[] rightArray = mergeSort(elements, middle + 1, right, counters);
    return merge(leftArray, rightArray, counters);
  }

  int[] merge(int[] leftArray, int[] rightArray, Counters counters) {
    int leftLen = leftArray.length;
    int rightLen = rightArray.length;

    int[] target = new int[leftLen + rightLen];
    int targetPos = 0;
    int leftPos = 0;
    int rightPos = 0;

    // As long as both lists contain elements...
    while (isLessThan(leftPos, leftLen, counters)
          && isLessThan(rightPos, rightLen, counters)) {
      counters.incIterations();

      // Which one is smaller?
      int leftValue = leftArray[leftPos];
      int rightValue = rightArray[rightPos];
      counters.addReads(2);

      counters.incComparisons();
      counters.incWrites();
      if (leftValue <= rightValue) {
        target[targetPos++] = leftValue;
        leftPos++;
      } else {
        target[targetPos++] = rightValue;
        rightPos++;
      }
    }

    // Copy the rest
    while (isLessThan(leftPos, leftLen, counters)) {
      counters.incIterations();
      target[targetPos++] = leftArray[leftPos++];
      counters.incReadsAndWrites();
    }
    while (isLessThan(rightPos, rightLen, counters)) {
      counters.incIterations();
      target[targetPos++] = rightArray[rightPos++];
      counters.incReadsAndWrites();
    }
    return target;
  }

  private boolean isLessThan(int a, int b, Counters counters) {
    counters.incComparisons();
    return a < b;
  }

  @Override
  public boolean isSuitableForInputSize(int size) {
    return size <= MAX_INPUT_SIZE;
  }

}
