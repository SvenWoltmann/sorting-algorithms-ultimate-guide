package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;

/**
 * Quicksort combined with Insertion Sort for small arrays.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class QuicksortImproved implements SortAlgorithm {

  private final int threshold;
  private final PartitioningAlgorithm quicksort;
  private final InsertionSort insertionSort;

  /**
   * Constructs the Quicksort instance.
   *
   * @param threshold when the array to be sorted is not longer than this
   *                  threshold, the algorithm switches to Insertion Sort
   * @param quicksort the quicksort algorithm to use
   */
  public QuicksortImproved(int threshold, PartitioningAlgorithm quicksort) {
    this.threshold = threshold;
    this.quicksort = quicksort;
    this.insertionSort = new InsertionSort();
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName() + "(threshold: " + threshold +
          ", partitioning: " + quicksort.getName() + ")";
  }

  @Override
  public void sort(int[] elements) {
    quicksort(elements, 0, elements.length - 1);
  }

  private void quicksort(int[] elements, int left, int right) {
    // End of recursion reached?
    if (left >= right) return;

    // Threshold for insertion sort reached?
    if (right - left < threshold) {
      insertionSort.sort(elements, left, right + 1);
      return;
    }

    int pivotPos = quicksort.partition(elements, left, right);
    quicksort(elements, left, pivotPos - 1);
    quicksort(elements, pivotPos + 1, right);
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    quicksort(elements, 0, elements.length - 1, counters);
  }

  private void quicksort(int[] elements, int left, int right,
                         Counters counters) {
    // Nothing to sort?
    if (left == right) return;

    // Threshold for insertion sort reached?
    if (right - left < threshold) {
      insertionSort.sort(elements, left, right + 1, counters);
      return;
    }

    int pivotPos = quicksort.partition(elements, left, right, counters);
    quicksort(elements, left, pivotPos - 1, counters);
    quicksort(elements, pivotPos + 1, right, counters);
  }

  @Override
  public boolean isSuitableForInputSize(int size) {
    return quicksort.isSuitableForInputSize(size);
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    return quicksort.isSuitableForSortedInput(size);
  }

}
