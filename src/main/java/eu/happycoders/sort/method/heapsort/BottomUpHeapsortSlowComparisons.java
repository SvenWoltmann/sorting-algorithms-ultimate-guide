package eu.happycoders.sort.method.heapsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.utils.NotImplementedException;

/**
 * Bottom-up heapsort implementation with slow comparisons (to show that bottom-up heapsort is
 * faster than regular heapsort if comparisons are expensive).
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
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
      if (heap[leafPos] < parent) {
        leafPos = getParentPos(leafPos);
      } else {
        break;
      }
    }
    return leafPos;
  }

  @Override
  public void sortWithCounters(int[] elements, Counters counters) {
    throw new NotImplementedException();
  }

  private void slowDown() {
    Thread.onSpinWait();
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    return false;
  }
}
