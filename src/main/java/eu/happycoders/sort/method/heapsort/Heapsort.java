package eu.happycoders.sort.method.heapsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.ArrayUtils;

public class Heapsort implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    // Phase 1
    buildHeap(elements);

    // Phase 2
    for (int swapToPos = elements.length - 1; swapToPos > 0; swapToPos--) {
      // Move root to end
      ArrayUtils.swap(elements, 0, swapToPos);

      // Fix remaining heap
      heapify(elements, swapToPos, 0);
    }
  }

  /**
   * Converts the given array to a "max heap".
   *
   * @param elements the elements to be converted
   */
  void buildHeap(int[] elements) {
    // "Find" the last parent node
    int lastParentNode = elements.length / 2 - 1;

    // Now heapify it from here on backwards
    for (int i = lastParentNode; i >= 0; i--) {
      heapify(elements, elements.length, i);
    }
  }

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
      if (leftChildPos < length && heap[leftChildPos] > heap[largestPos]) {
        largestPos = leftChildPos;
      }
      if (rightChildPos < length && heap[rightChildPos] > heap[largestPos]) {
        largestPos = rightChildPos;
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
    // Phase 1
    buildHeap(elements, counters);

    // Phase 2
    Counters countersPhase2 = counters.getPhase2();
    for (int swapToPos = elements.length - 1; swapToPos > 0; swapToPos--) {
      // Move root to end
      ArrayUtils.swap(elements, 0, swapToPos);
      countersPhase2.addReadsAndWrites(2);

      // Fix remaining heap
      heapify(elements, swapToPos, 0, countersPhase2);
    }
  }

  void buildHeap(int[] elements, Counters counters) {
    // "Find" the last parent node
    int lastParentNode = elements.length / 2 - 1;

    // Now fix it from here on backwards
    for (int i = lastParentNode; i >= 0; i--) {
      counters.incIterations();
      heapify(elements, elements.length, i, counters);
    }
  }

  void heapify(int[] heap, int length, int parentPos, Counters counters) {
    while (true) {
      counters.incIterations();
      int leftChildPos = parentPos * 2 + 1;
      int rightChildPos = parentPos * 2 + 2;

      // Find the largest element
      int largestPos = parentPos;
      if (leftChildPos < length) {
        counters.incComparisons();
        counters.addReads(2);
        if (heap[leftChildPos] > heap[largestPos]) {
          largestPos = leftChildPos;
        }
      }
      if (rightChildPos < length) {
        counters.incComparisons();
        counters.addReads(2);
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
      counters.addReadsAndWrites(2);

      // ... and fix again starting at the child we moved the parent to
      parentPos = largestPos;
    }
  }

}
