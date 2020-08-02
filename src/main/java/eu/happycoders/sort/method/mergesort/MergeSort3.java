package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.*;

import java.util.Arrays;

/**
 * Merge sort implementation for performance tests.
 *
 * <p>
 * This implementation creates new arrays in the divide phase, so the
 * additional space complexity is <strong>O(n log n)</strong>.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class MergeSort3 implements SortAlgorithm {

  // Otherwise we run out of heap
  private static final int MAX_INPUT_SIZE = 1 << 28;

  @Override
  public void sort(int[] elements) {
    mergeSort(elements);
  }

  private void mergeSort(int[] elements) {
    // End of recursion reached?
    int length = elements.length;
    if (length == 1) return;

    int middle = length / 2;

    // Create left + right arrays
    int[] left = Arrays.copyOfRange(elements, 0, middle);
    int[] right = Arrays.copyOfRange(elements, middle, length);

    // Merge sort them
    mergeSort(left);
    mergeSort(right);
    merge(elements, left, right);
  }

  void merge(int[] target, int[] leftArray, int[] rightArray) {
    int leftLen = leftArray.length;
    int rightLen = rightArray.length;

    int targetPos = 0;
    int leftPos = 0;
    int rightPos = 0;

    // As long as both lists contain elements...
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
    // Copying the rest
    while (leftPos < leftLen) {
      target[targetPos++] = leftArray[leftPos++];
    }
    while (rightPos < rightLen) {
      target[targetPos++] = rightArray[rightPos++];
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    mergeSort(elements, counters);
  }

  private void mergeSort(int[] elements, Counters counters) {
    // End of recursion reached?
    int length = elements.length;
    if (length == 1) return;

    int middle = length / 2;

    // Create left + right arrays
    int[] left = Arrays.copyOfRange(elements, 0, middle);
    int[] right = Arrays.copyOfRange(elements, middle, length);
    counters.addReadsAndWrites(length);

    // Merge sort them
    mergeSort(left, counters);
    mergeSort(right, counters);
    merge(elements, left, right, counters);
  }

  void merge(int[] target, int[] leftArray, int[] rightArray,
             Counters counters) {
    int leftLen = leftArray.length;
    int rightLen = rightArray.length;

    int targetPos = 0;
    int leftPos = 0;
    int rightPos = 0;

    // As long as both lists contain elements...
    while (leftPos < leftLen && rightPos < rightLen) {
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
    // Copying the rest
    while (leftPos < leftLen) {
      counters.incIterations();
      target[targetPos++] = leftArray[leftPos++];
      counters.incReadsAndWrites();
    }
    while (rightPos < rightLen) {
      counters.incIterations();
      target[targetPos++] = rightArray[rightPos++];
      counters.incReadsAndWrites();
    }
  }

  @Override
  public boolean isSuitableForInputSize(int size) {
    return size <= MAX_INPUT_SIZE;
  }

}
