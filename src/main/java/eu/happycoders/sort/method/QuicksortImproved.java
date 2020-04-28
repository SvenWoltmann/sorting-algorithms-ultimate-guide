package eu.happycoders.sort.method;

import eu.happycoders.sort.method.Quicksort.PivotStrategy;

/**
 * Quicksort combined with Insertion Sort for small arrays.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class QuicksortImproved implements SortAlgorithm {

  private final int threshold;
  private final PivotStrategy pivotStrategy; // just for the getName() method
  private final Quicksort standardQuicksort;
  private final InsertionSort insertionSort;

  /**
   * Constructs the Quicksort instance.
   *
   * @param threshold     when the array to be sorted is not longer than this
   *                      threshold, the algorithm switches to Insertion Sort
   * @param pivotStrategy the pivot strategy to use
   */
  public QuicksortImproved(int threshold, PivotStrategy pivotStrategy) {
    this.threshold = threshold;
    this.pivotStrategy = pivotStrategy;
    this.standardQuicksort = new Quicksort(pivotStrategy);
    this.insertionSort = new InsertionSort();
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName() + "(threshold: " + threshold +
          ", pivot: " + pivotStrategy + ")";
  }

  @Override
  public void sort(int[] elements) {
    quicksort(elements, 0, elements.length - 1);
  }

  private void quicksort(int[] elements, int left, int right) {
    // Nothing to sort?
    if (left == right) return;

    // Threshold for insertion sort reached?
    if (right - left < threshold) {
      insertionSort.sort(elements, left, right + 1);
      return;
    }

    int pivotPos = standardQuicksort.partition(elements, left, right);
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

    int pivotPos = standardQuicksort.partition(elements, left, right, counters);
    quicksort(elements, left, pivotPos - 1, counters);
    quicksort(elements, pivotPos + 1, right, counters);
  }

  @Override
  public boolean isSuitableForInputSize(int size) {
    return standardQuicksort.isSuitableForInputSize(size);
  }

  @Override
  public boolean isSuitableForSortedInput() {
    return standardQuicksort.isSuitableForSortedInput();
  }

}
