package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.*;

/**
 * Merge sort implementation for performance tests.
 *
 * <p>
 * This implementation divides directly on the input array and creates new
 * arrays only in the merge phase, copying them immediately back into the
 * input array after merging.
 *
 * <p>
 * Additional space complexity is <strong>O(n)</strong>.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class MergeSort2 implements SortAlgorithm {

  // Otherwise we run out of heap
  private static final int MAX_INPUT_SIZE = 1 << 28;

  @Override
  public void sort(int[] elements) {
    mergeSort(elements, 0, elements.length - 1);
  }

  private void mergeSort(int[] elements, int left, int right) {
    // End of recursion reached?
    if (left == right) return;

    int middle = left + (right - left) / 2;
    mergeSort(elements, left, middle);
    mergeSort(elements, middle + 1, right);
    merge(elements, left, middle, right);
  }

  void merge(int[] elements, int leftStart, int leftEnd, int rightEnd) {
    int leftPos = leftStart;
    int rightPos = leftEnd + 1;

    int length = rightEnd + 1 - leftPos;
    int[] target = new int[length];
    int targetPos = 0;

    // As long as both lists contain elements...
    while (leftPos <= leftEnd && rightPos <= rightEnd) {
      // Which one is smaller?
      int leftValue = elements[leftPos];
      int rightValue = elements[rightPos];
      if (leftValue <= rightValue) {
        target[targetPos++] = leftValue;
        leftPos++;
      } else {
        target[targetPos++] = rightValue;
        rightPos++;
      }
    }
    // Copying the rest
    while (leftPos <= leftEnd) {
      target[targetPos++] = elements[leftPos++];
    }
    while (rightPos <= rightEnd) {
      target[targetPos++] = elements[rightPos++];
    }
    // Write temporary array back into array to be sorted
    System.arraycopy(target, 0, elements, leftStart, length);
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    mergeSort(elements, 0, elements.length - 1, counters);
  }

  private void mergeSort(int[] elements, int left, int right,
                         Counters counters) {
    // End of recursion reached?
    if (left == right) return;

    int middle = left + (right - left) / 2;
    mergeSort(elements, left, middle, counters);
    mergeSort(elements, middle + 1, right, counters);
    merge(elements, left, middle, right, counters);
  }

  void merge(int[] elements, int leftStart, int leftEnd, int rightEnd,
             Counters counters) {
    int leftPos = leftStart;
    int rightPos = leftEnd + 1;

    int length = rightEnd + 1 - leftPos;
    int[] target = new int[length];
    int targetPos = 0;

    // As long as both lists contain elements...
    while (leftPos <= leftEnd && rightPos <= rightEnd) {
      counters.incIterations();

      // Which one is smaller?
      int leftValue = elements[leftPos];
      int rightValue = elements[rightPos];
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
    while (leftPos <= leftEnd) {
      counters.incIterations();
      target[targetPos++] = elements[leftPos++];
      counters.incReadsAndWrites();
    }
    while (rightPos <= rightEnd) {
      counters.incIterations();
      target[targetPos++] = elements[rightPos++];
      counters.incReadsAndWrites();
    }
    // Write temporary array back into array to be sorted
    System.arraycopy(target, 0, elements, leftStart, length);
    counters.addReadsAndWrites(length);
  }

  @Override
  public boolean isSuitableForInputSize(int size) {
    return size <= MAX_INPUT_SIZE;
  }

}
