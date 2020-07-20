package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.utils.ArrayUtils;

/**
 * Dual-pivot Quicksort implementation for performance tests.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class DualPivotQuicksort implements SortAlgorithm {

  private final PivotStrategy pivotStrategy;

  public DualPivotQuicksort(PivotStrategy pivotStrategy) {
    this.pivotStrategy = pivotStrategy;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName() + "(pivot: " + pivotStrategy + ")";
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    // Don't run tests for sorted input with pivot strategy LEFT_RIGHT.
    // This will go (n - 2) x into recursion.
    return pivotStrategy != PivotStrategy.LEFT_RIGHT || size <= 2 << 14;
  }

  @Override
  public void sort(int[] elements) {
    quicksort(elements, 0, elements.length - 1);
  }

  private void quicksort(int[] elements, int left, int right) {
    // End of recursion reached?
    if (left >= right) return;

    int[] pivotPos = partition(elements, left, right);
    int p0 = pivotPos[0];
    int p1 = pivotPos[1];
    quicksort(elements, left, p0 - 1);
    quicksort(elements, p0 + 1, p1 - 1);
    quicksort(elements, p1 + 1, right);
  }

  int[] partition(int[] elements, int left, int right) {
    findPivotsAndMoveToLeftRight(elements, left, right);
    int leftPivot = elements[left];
    int rightPivot = elements[right];

    int leftPartitionEnd = left + 1;
    int leftIndex = left + 1;
    int rightIndex = right - 1;

    while (leftIndex <= rightIndex) {

      // elements < left pivot element?
      if (elements[leftIndex] < leftPivot) {
        ArrayUtils.swap(elements, leftIndex, leftPartitionEnd);
        leftPartitionEnd++;
      }

      // elements >= right pivot element?
      else if (elements[leftIndex] >= rightPivot) {
        while (elements[rightIndex] > rightPivot && leftIndex < rightIndex)
          rightIndex--;
        ArrayUtils.swap(elements, leftIndex, rightIndex);
        rightIndex--;
        if (elements[leftIndex] < leftPivot) {
          ArrayUtils.swap(elements, leftIndex, leftPartitionEnd);
          leftPartitionEnd++;
        }
      }
      leftIndex++;
    }
    leftPartitionEnd--;
    rightIndex++;

    // move pivots to their final positions
    ArrayUtils.swap(elements, left, leftPartitionEnd);
    ArrayUtils.swap(elements, right, rightIndex);

    return new int[]{leftPartitionEnd, rightIndex};
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    quicksort(elements, 0, elements.length - 1, counters);
  }

  private void quicksort(int[] elements, int left, int right,
                         Counters counters) {
    // End of recursion reached?
    if (left >= right) return;

    int[] pivotPos = partition(elements, left, right, counters);
    int p0 = pivotPos[0];
    int p1 = pivotPos[1];
    quicksort(elements, left, p0 - 1, counters);
    quicksort(elements, p0 + 1, p1 - 1, counters);
    quicksort(elements, p1 + 1, right, counters);
  }

  int[] partition(int[] elements, int left, int right, Counters counters) {
    findPivotsAndMoveToLeftRight(elements, left, right);
    int leftPivot = elements[left];
    int rightPivot = elements[right];
    counters.addReads(2);

    int leftPartitionEnd = left + 1;
    int leftIndex = left + 1;
    int rightIndex = right - 1;

    while (leftIndex <= rightIndex) {
      counters.incIterations();

      // elements <= left pivot element?
      counters.incReads();
      counters.incComparisons();
      if (elements[leftIndex] < leftPivot) {
        ArrayUtils.swap(elements, leftIndex, leftPartitionEnd);
        counters.addReadsAndWrites(2);
        leftPartitionEnd++;
      } else {
        counters.incReads();
        counters.incComparisons();
        // elements >= right pivot element?
        if (elements[leftIndex] >= rightPivot) {
          while (leftIndex < rightIndex) {
            counters.incIterations();
            counters.incReads();
            counters.incComparisons();
            if (!(elements[rightIndex] > rightPivot)) break;
            rightIndex--;
          }
          ArrayUtils.swap(elements, leftIndex, rightIndex);
          counters.addReadsAndWrites(2);
          rightIndex--;
          counters.incReads();
          counters.incComparisons();
          if (elements[leftIndex] < leftPivot) {
            ArrayUtils.swap(elements, leftIndex, leftPartitionEnd);
            counters.addReadsAndWrites(2);
            leftPartitionEnd++;
          }
        }
      }
      leftIndex++;
    }
    leftPartitionEnd--;
    rightIndex++;

    // move pivots to their final positions
    ArrayUtils.swap(elements, left, leftPartitionEnd);
    ArrayUtils.swap(elements, right, rightIndex);
    counters.addReadsAndWrites(4);

    return new int[]{leftPartitionEnd, rightIndex};
  }

  private void findPivotsAndMoveToLeftRight(int[] elements,
                                            int left, int right) {
    switch (pivotStrategy) {
      case LEFT_RIGHT -> {
        if (elements[left] > elements[right]) {
          ArrayUtils.swap(elements, left, right);
        }
      }

      case THIRDS -> {
        int len = right - left + 1;
        int firstPos = left + (len - 1) / 3;
        int secondPos = right - (len - 2) / 3;

        int first = elements[firstPos];
        int second = elements[secondPos];

        if (first > second) {
          if (secondPos == right) {
            if (firstPos == left) {
              ArrayUtils.swap(elements, left, right);
            } else {
              // 3-way swap
              elements[right] = first;
              elements[firstPos] = elements[left];
              elements[left] = second;
            }
          } else if (firstPos == left) {
            // 3-way swap
            elements[left] = second;
            elements[secondPos] = elements[right];
            elements[right] = first;
          } else {
            ArrayUtils.swap(elements, firstPos, right);
            ArrayUtils.swap(elements, secondPos, left);
          }
        } else {
          if (secondPos != right)
            ArrayUtils.swap(elements, secondPos, right);
          if (firstPos != left)
            ArrayUtils.swap(elements, firstPos, left);
        }
      }

      default -> throw new IllegalStateException("Unexpected value: " + pivotStrategy);
    }
  }

  public enum PivotStrategy {LEFT_RIGHT, THIRDS}

}
