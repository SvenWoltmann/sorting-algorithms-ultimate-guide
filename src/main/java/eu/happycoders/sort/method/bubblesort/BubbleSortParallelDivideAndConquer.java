package eu.happycoders.sort.method.bubblesort;

/**
 * Parallel Bubble Sort implementation using a divide-and-conquer approach.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BubbleSortParallelDivideAndConquer extends BubbleSortParallel {

  /**
   * Sorts a partition of the elements.
   *
   * @param elements the elements
   * @param startPos the partition's start position
   * @param endPos   the partition's end position
   * @param even     whether it's the even or odd step of an iteration
   * @return whether any elements were swapped
   */
  @Override
  boolean sortPartition(int[] elements, int startPos, int endPos,
                        boolean even) {
    boolean swapped = false;

    // Step 1, 3, 5, ...
    // iterate over all elements of the partition
    if (!even) {
      for (int i = startPos; i < endPos - 1; i++) {
        int left = elements[i];
        int right = elements[i + 1];
        if (left > right) {
          elements[i + 1] = left;
          elements[i] = right;
          swapped = true;
        }
      }
    }

    // Step 2, 4, 6, ...
    // compare this partition's last element with the next partition's first
    else if (endPos < elements.length - 1) {
      int left = elements[endPos - 1];
      int right = elements[endPos];
      if (left > right) {
        elements[endPos] = left;
        elements[endPos - 1] = right;
        swapped = true;
      }
    }

    return swapped;
  }

}
