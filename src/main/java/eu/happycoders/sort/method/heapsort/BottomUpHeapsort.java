package eu.happycoders.sort.method.heapsort;

import eu.happycoders.sort.method.Counters;

public class BottomUpHeapsort extends Heapsort {

  /**
   * "Fixes" a max heap starting at the given parent position.
   *
   * @param heap    the heap to be fixed
   * @param length  the number of elements in the array that belong to the
   *                heap
   * @param rootPos the parent position
   */
  @Override
  void heapify(int[] heap, int length, int rootPos) {
    int leafPos = findLeaf(heap, length, rootPos);
    int nodePos = findTargetNodeBottomUp(heap, rootPos, leafPos);

    if (rootPos == nodePos) return;

    // Move all elements starting at nodePos to parent, move root to nodePos
    int nodeValue = heap[nodePos];
    heap[nodePos] = heap[rootPos];

    while (nodePos > rootPos) {
      int parentPos = getParentPos(nodePos);
      int parentValue = heap[parentPos];
      heap[parentPos] = nodeValue;
      nodePos = getParentPos(nodePos);
      nodeValue = parentValue;
    }
  }

  int findLeaf(int[] heap, int length, int rootPos) {
    int pos = rootPos;
    int leftChildPos = pos * 2 + 1;
    int rightChildPos = pos * 2 + 2;

    // Two child exist?
    while (rightChildPos < length) {
      if (heap[rightChildPos] > heap[leftChildPos]) {
        pos = rightChildPos;
      } else {
        pos = leftChildPos;
      }
      leftChildPos = pos * 2 + 1;
      rightChildPos = pos * 2 + 2;
    }

    // One child exist?
    if (leftChildPos < length) {
      pos = leftChildPos;
    }

    return pos;
  }

  int findTargetNodeBottomUp(int[] heap, int rootPos, int leafPos) {
    int parent = heap[rootPos];
    while (leafPos != rootPos && heap[leafPos] < parent) {
      leafPos = getParentPos(leafPos);
    }
    return leafPos;
  }

  int getParentPos(int pos) {
    return (pos - 1) / 2;
  }

  @Override
  void heapify(int[] heap, int length, int rootPos, Counters counters) {
    int leafPos = findLeaf(heap, length, rootPos, counters);
    int nodePos = findTargetNodeBottomUp(heap, rootPos, leafPos, counters);

    if (rootPos == nodePos) return;

    // Move all elements starting at nodePos to parent, move root to nodePos
    counters.incReads();
    int nodeValue = heap[nodePos];
    counters.incReadsAndWrites();
    heap[nodePos] = heap[rootPos];

    while (nodePos > rootPos) {
      counters.incIterations();
      int parentPos = getParentPos(nodePos);
      counters.incReadsAndWrites();
      int parentValue = heap[parentPos];
      heap[parentPos] = nodeValue;
      nodePos = parentPos;
      nodeValue = parentValue;
    }
  }

  private int findLeaf(int[] heap, int length, int rootPos, Counters counters) {
    int pos = rootPos;
    int leftChildPos = pos * 2 + 1;
    int rightChildPos = pos * 2 + 2;

    while (rightChildPos < length) {
      counters.incIterations();
      counters.addReads(2);
      counters.incComparisons();
      if (heap[rightChildPos] > heap[leftChildPos]) {
        pos = rightChildPos;
      } else {
        pos = leftChildPos;
      }
      leftChildPos = pos * 2 + 1;
      rightChildPos = pos * 2 + 2;
    }

    if (leftChildPos < length) {
      pos = leftChildPos;
    }

    return pos;
  }

  private int findTargetNodeBottomUp(int[] heap, int rootPos, int leafPos,
                                     Counters counters) {
    counters.incReads();
    int parentValue = heap[rootPos];
    int nodePos = leafPos;
    while (nodePos != rootPos && nodeSmallerThanParent(heap[nodePos],
          parentValue, counters)) {
      counters.incIterations();
      nodePos = getParentPos(nodePos);
    }
    return nodePos;
  }

  private boolean nodeSmallerThanParent(int nodeValue, int parentValue,
                                        Counters counters) {
    counters.incReads();
    counters.incComparisons();
    return nodeValue < parentValue;
  }

}
