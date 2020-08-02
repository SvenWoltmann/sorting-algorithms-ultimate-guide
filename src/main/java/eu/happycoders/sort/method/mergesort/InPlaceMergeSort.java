package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;

/**
 * Trivial In-place Merge sort implementation for performance tests.
 *
 * <p>
 * This implementation has a space complexity of O(1), however its time
 * complexity is O(n² log n) due to the two nested loops in the merge() method.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class InPlaceMergeSort implements SortAlgorithm {

  // Otherwise we run out of heap
//  private static final int MAX_INPUT_SIZE = 1 << 28;

  @Override
  public void sort(int[] elements) {
    int length = elements.length;
    mergeSort(elements, 0, length - 1);
  }

  private void mergeSort(int[] elements, int left, int right) {
    // End of recursion reached?
    if (left == right) return;

    int middle = left + (right - left) / 2;
    mergeSort(elements, left, middle);
    mergeSort(elements, middle + 1, right);
    merge(elements, left, middle + 1, right);
  }

void merge(int[] elements, int leftPos, int rightPos, int rightEnd) {
  int leftEnd = rightPos - 1;

  while (leftPos <= leftEnd && rightPos <= rightEnd) {
    // Which one is smaller?
    int leftValue = elements[leftPos];
    int rightValue = elements[rightPos];
    if (leftValue <= rightValue) {
      leftPos++;
    } else {
      // Move all the elements from leftPos to excluding rightPos one field
      // to the right
      int movePos = rightPos;
      while (movePos > leftPos) {
        elements[movePos] = elements[movePos - 1];
        movePos--;
      }
      elements[leftPos] = rightValue;
      leftPos++;
      leftEnd++;
      rightPos++;
    }
  }
}

  @Override
  public void sort(int[] elements, Counters counters) {
    int length = elements.length;
    mergeSort(elements, 0, length - 1, counters);
  }

  private void mergeSort(int[] elements, int left, int right,
                         Counters counters) {
    // End of recursion reached?
    if (left == right) return;

    int middle = left + (right - left) / 2;
    mergeSort(elements, left, middle, counters);
    mergeSort(elements, middle + 1, right, counters);
    merge(elements, left, middle + 1, right, counters);
  }

  private void merge(int[] elements, int leftPos, int rightPos, int rightEnd,
                     Counters counters) {
    int leftEnd = rightPos - 1;

    while (isLessThanOrEqual(leftPos, leftEnd, counters) && isLessThanOrEqual(rightPos, rightEnd, counters)) {
      // Which one is smaller?
      int leftValue = elements[leftPos];
      int rightValue = elements[rightPos];
      counters.addReads(2);

      counters.incComparisons();
      if (leftValue <= rightValue) {
        leftPos++;
      } else {
        // Move all the elements from leftPos to excluding rightPos one field
        // to the right
        int movePos = rightPos;
        while (isGreaterThan(movePos, leftPos, counters)) {
          counters.incReadsAndWrites();
          elements[movePos] = elements[movePos - 1];
          movePos--;
        }
        counters.incWrites();
        elements[leftPos] = rightValue;
        leftPos++;
        leftEnd++;
        rightPos++;
      }
    }
  }

  private boolean isLessThanOrEqual(int a, int b, Counters counters) {
    counters.incComparisons();
    return a <= b;
  }

  private boolean isGreaterThan(int a, int b, Counters counters) {
    counters.incComparisons();
    return a > b;
  }

}
