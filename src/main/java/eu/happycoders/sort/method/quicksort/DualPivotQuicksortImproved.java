package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.InsertionSort;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.quicksort.DualPivotQuicksort.PivotStrategy;

/**
 * Dual-pivot Quicksort implementation for performance tests.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class DualPivotQuicksortImproved implements SortAlgorithm {

  private final int threshold;
  private final PivotStrategy pivotStrategy; // just for the getName() method
  private final DualPivotQuicksort standardQuicksort;
  private final InsertionSort insertionSort;

  /**
   * Constructs the Dual-pivot Quicksort instance.
   *
   * @param threshold when the array to be sorted is not longer than this threshold, the algorithm
   *     switches to Insertion Sort
   * @param pivotStrategy the pivot strategy to use
   */
  public DualPivotQuicksortImproved(int threshold, PivotStrategy pivotStrategy) {
    this.threshold = threshold;
    this.pivotStrategy = pivotStrategy;
    this.standardQuicksort = new DualPivotQuicksort(pivotStrategy);
    this.insertionSort = new InsertionSort();
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName()
        + "(threshold: "
        + threshold
        + ", pivot: "
        + pivotStrategy
        + ")";
  }

  @Override
  public void sort(int[] elements) {
    quicksort(elements, 0, elements.length - 1);
  }

  private void quicksort(int[] elements, int left, int right) {
    // End of recursion reached?
    if (left >= right) {
      return;
    }

    // Threshold for insertion sort reached?
    if (right - left < threshold) {
      insertionSort.sort(elements, left, right + 1);
      return;
    }

    int[] pivotPos = standardQuicksort.partition(elements, left, right);
    int p0 = pivotPos[0];
    int p1 = pivotPos[1];
    quicksort(elements, left, p0 - 1);
    quicksort(elements, p0 + 1, p1 - 1);
    quicksort(elements, p1 + 1, right);
  }

  @Override
  public void sortWithCounters(int[] elements, Counters counters) {
    quicksortWithCounters(elements, 0, elements.length - 1, counters);
  }

  private void quicksortWithCounters(int[] elements, int left, int right, Counters counters) {
    // End of recursion reached?
    if (left >= right) {
      return;
    }

    // Threshold for insertion sort reached?
    if (right - left < threshold) {
      insertionSort.sortWithCounters(elements, left, right + 1, counters);
      return;
    }

    int[] pivotPos = standardQuicksort.partitionWithCounters(elements, left, right, counters);
    int p0 = pivotPos[0];
    int p1 = pivotPos[1];
    quicksortWithCounters(elements, left, p0 - 1, counters);
    quicksortWithCounters(elements, p0 + 1, p1 - 1, counters);
    quicksortWithCounters(elements, p1 + 1, right, counters);
  }

  @Override
  public boolean isSuitableForInputSize(int size) {
    return standardQuicksort.isSuitableForInputSize(size);
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    return standardQuicksort.isSuitableForSortedInput(size);
  }
}
