package eu.happycoders.sort.method.bubblesort;

/**
 * Parallel Bubble Sort implementation using the "odd-even" algorithm.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BubbleSortParallelOddEven extends BubbleSortParallel {

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

    // Odd steps 1, 3, 5, ... --> start at the first element
    // Even steps 2, 4, 6, ... --> start at the second element
    if (even) startPos++;

    for (int i = startPos; i < endPos && i < elements.length - 1; i += 2) {
      int left = elements[i];
      int right = elements[i + 1];
      if (left > right) {
        elements[i + 1] = left;
        elements[i] = right;
        swapped = true;
      }
    }

    return swapped;
  }

}
