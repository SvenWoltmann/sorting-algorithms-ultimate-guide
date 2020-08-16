package eu.happycoders.sort.method.heapsort;

import eu.happycoders.sort.method.Counters;

public class BottomUpHeapsortSlowComparisons extends BottomUpHeapsort {

  @Override
  int findLeaf(int[] heap, int length, int rootPos) {
    int pos = rootPos;
    int leftChildPos = pos * 2 + 1;
    int rightChildPos = pos * 2 + 2;

    while (rightChildPos < length) {
      slowDown();
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

  @Override
  int findTargetNodeBottomUp(int[] heap, int rootPos, int leafPos) {
    int parent = heap[rootPos];
    while (leafPos != rootPos) {
      slowDown();
      if (!(heap[leafPos] < parent)) break;
      leafPos = getParentPos(leafPos);
    }
    return leafPos;
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
