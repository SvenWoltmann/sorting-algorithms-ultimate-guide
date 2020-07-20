package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.*;
import eu.happycoders.sort.utils.ArrayUtils;

/**
 * Quicksort implementation for performance tests, supporting various pivot
 * strategies.
 *
 * <p>
 * Variant 2: moves pivot element to right sub-array during comparisons and
 * swaps, and remembers position change
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class QuicksortVariant2 implements SortAlgorithm, PartitioningAlgorithm {

  private final PivotStrategy pivotStrategy;

  public QuicksortVariant2(PivotStrategy pivotStrategy) {
    this.pivotStrategy = pivotStrategy;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName() + "(pivot: " + pivotStrategy + ")";
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    return (pivotStrategy != PivotStrategy.LEFT
          && pivotStrategy != PivotStrategy.RIGHT) || size <= 2 << 12;
  }

  @Override
  public void sort(int[] elements) {
    quicksort(elements, 0, elements.length - 1);
  }

  private void quicksort(int[] elements, int left, int right) {
    // End of recursion reached?
    if (left >= right) return;

    int pivotPos = partition(elements, left, right);

    quicksort(elements, left, pivotPos - 1);
    quicksort(elements, pivotPos + 1, right);
  }

  @Override
  public int partition(int[] elements, int left, int right) {
    int pivotPos =
          PivotHelper.findPivotPos(elements, left, right, pivotStrategy);
    int pivot = elements[pivotPos];

    int i = left;
    int j = pivotPos == right ? right - 1 : right;
    while (i < j) {
      // Find the first element >= pivot
      while (elements[i] < pivot) {
        i++;
      }

      // Find the last element < pivot
      while (j > left && elements[j] >= pivot) {
        j--;
      }

      // If the greater element is left of the lesser element, switch them
      if (i < j) {
        ArrayUtils.swap(elements, i, j);

        // Remember new pivot pos
        if (i == pivotPos) pivotPos = j;
        else if (j == pivotPos) pivotPos = i;

        i++;
        j--;
      }
    }

    // i == j means we haven't checked this index yet.
    // Move i right if necessary so that i marks the start of the right array.
    if (i == j && elements[i] < pivot) {
      i++;
    }

    // Move pivot element to its final position
    if (elements[i] != pivot) {
      ArrayUtils.swap(elements, i, pivotPos);
    }
    return i;
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    quicksort(elements, 0, elements.length - 1, counters);
  }

  private void quicksort(int[] elements, int left, int right,
                         Counters counters) {
    // End of recursion reached?
    if (left >= right) return;

    int pivotPos = partition(elements, left, right, counters);
    quicksort(elements, left, pivotPos - 1, counters);
    quicksort(elements, pivotPos + 1, right, counters);
  }

  public int partition(int[] elements, int left, int right, Counters counters) {
    int pivotPos =
          PivotHelper.findPivotPos(elements, left, right, pivotStrategy);
    int pivot = elements[pivotPos];

    int i = left;
    int j = pivotPos == right ? right - 1 : right;
    while (i < j) {
      counters.incIterations();

      // Find the first element >= pivot
      while (true) {
        counters.incComparisons();
        counters.incReads();
        if (!(elements[i] < pivot)) break;
        i++;
      }

      // Find the last element < pivot
      while (true) {
        counters.incComparisons();
        counters.incReads();
        if (!(j > left && elements[j] >= pivot)) break;
        j--;
      }

      // If the greater element is left of the lesser element, switch them
      if (i < j) {
        ArrayUtils.swap(elements, i, j);
        counters.addReadsAndWrites(2);

        // Remember new pivot pos
        if (i == pivotPos) pivotPos = j;
        else if (j == pivotPos) pivotPos = i;

        i++;
        j--;
      }
    }

    // i == j means we haven't checked this index yet.
    // Move i right if necessary so that i marks the start of the right array.
    if (i == j) {
      counters.incReads();
      counters.incComparisons();
      if (elements[i] < pivot) {
        i++;
      }
    }

    // Move pivot element to its final position
    counters.incReads();
    counters.incComparisons();
    if (elements[i] != pivot) {
      ArrayUtils.swap(elements, i, pivotPos);
      counters.addReadsAndWrites(2);
    }
    return i;
  }

}
