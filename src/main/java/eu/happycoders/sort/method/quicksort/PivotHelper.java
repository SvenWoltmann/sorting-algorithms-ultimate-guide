package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.utils.ArrayUtils;

import java.util.concurrent.ThreadLocalRandom;

public class PivotHelper {

  static void findPivotAndMoveRight(int[] elements, int left, int right,
                                    PivotStrategy pivotStrategy) {
    int pivotPos = findPivotPos(elements, left, right, pivotStrategy);
    if (pivotPos != right)
      ArrayUtils.swap(elements, pivotPos, right);
  }

  static int findPivotPos(int[] elements, int left, int right,
                          PivotStrategy pivotStrategy) {
    return switch (pivotStrategy) {
      case RIGHT -> right;
      case RANDOM -> ThreadLocalRandom.current().nextInt(left, right + 1);
      case LEFT -> left;
      case MIDDLE -> {
        // optimization: if length is 2, take right element, saves one swap
        int rl = right - left;
        yield rl < 2 ? right : left + rl / 2;
      }
      case MEDIAN3 -> getMedian3Pos(elements, left, right);
      default -> throw new IllegalStateException("Unexpected value: " + pivotStrategy);
    };
  }

  private static int getMedian3Pos(int[] elements, int left, int right) {
    int rl = right - left;
    if (rl < 2) {
      return right;
    }

    // We must split the array into 4 parts and take the elements at the
    // borders. If we took the left or right element, we'll end up with O(n²)
    // for descending input data when swapping the pivot element with the
    // rightmost element.

    // Reason: First, the middle element will be chosen as Pivot element and
    // moved to the right, therefore the smallest element will be in the middle.

    // Example: 100 99 98 ... 53 52 1 50 49 ... 4 3 2 51

    // After swapping large elements to the right and small elements to the
    // left, the second smallest element will be at the left.

    // Example: 2 3 4 ... 49 50 1 52 53 ... 98 99 100 51

    // In the next iteration, the middle will be the last element of the left
    // sub-array. Therefore the two smallest elements of the array will be at
    // its borders:

    // Example: 2 3 4 ... 49 50 1

    // The median will now be the left element, which will not split the left
    // array into two equal-sized areas, but one of size 1 and one of size
    // n-2. Therefore, we'll end up with quadratic time instead of
    // quasi-linear time.

    int middle = left + rl / 2;
    int first = left + (middle - left) / 2;
    int last = right - (right - middle) / 2;

    int l = elements[first];
    int m = elements[middle];
    int r = elements[last];

    if (l < r) {
      if (l >= m) {
        return first;
      } else if (r < m) {
        return last;
      }
    } else {
      if (l < m) {
        return first;
      } else if (r >= m) {
        return last;
      }
    }
    return middle;
  }

}
