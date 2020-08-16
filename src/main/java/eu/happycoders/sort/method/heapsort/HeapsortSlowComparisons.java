package eu.happycoders.sort.method.heapsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.utils.ArrayUtils;

public class HeapsortSlowComparisons extends Heapsort {

  /**
   * "Fixes" a max heap starting at the given parent position.
   *
   * @param heap      the heap to be fixed
   * @param length    the number of elements in the array that belong to the
   *                  heap
   * @param parentPos the parent position
   */
  void heapify(int[] heap, int length, int parentPos) {
    while (true) {
      int leftChildPos = parentPos * 2 + 1;
      int rightChildPos = parentPos * 2 + 2;

      // Find the largest element
      int largestPos = parentPos;
      if (leftChildPos < length) {
        slowDown();
        if (heap[leftChildPos] > heap[largestPos]) {
          largestPos = leftChildPos;
        }
      }
      if (rightChildPos < length) {
        slowDown();
        if (heap[rightChildPos] > heap[largestPos]) {
          largestPos = rightChildPos;
        }
      }

      // largestPos is now either parentPos, leftChildPos or rightChildPos.
      // If it's the parent, we're done
      if (largestPos == parentPos) {
        break;
      }

      // If it's not the parent, then switch!
      ArrayUtils.swap(heap, parentPos, largestPos);

      // ... and fix again starting at the child we moved the parent to
      parentPos = largestPos;
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    throw new Error("Not implemented");
  }

  private void slowDown() {
    // Thread.sleep(0, 1) takes too long
    long nanos = System.nanoTime();
    while (nanos == System.nanoTime()) {
    }
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    return false;
  }
}
