package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.PartitioningAlgorithm;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.ArrayUtils;

/**
 * Quicksort implementation for performance tests, supporting various pivot strategies.
 *
 * <p>Variant 3: leaves pivot element where it is and swaps it with the rightmost element of the
 * left sub-array if it ends up in the left part
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class QuicksortVariant3 implements SortAlgorithm, PartitioningAlgorithm {

  private final PivotStrategy pivotStrategy;

  public QuicksortVariant3(PivotStrategy pivotStrategy) {
    this.pivotStrategy = pivotStrategy;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName() + "(pivot: " + pivotStrategy + ")";
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    return pivotStrategy != PivotStrategy.LEFT && pivotStrategy != PivotStrategy.RIGHT
        || size <= 2 << 12;
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

    int pivotPos = partition(elements, left, right);

    quicksort(elements, left, pivotPos - 1);
    quicksort(elements, pivotPos + 1, right);
  }

  @Override
  public int partition(int[] elements, int left, int right) {
    int pivotPos = PivotHelper.findPivotPos(elements, left, right, pivotStrategy);
    int pivot = elements[pivotPos];

    int i = left;
    int j = pivotPos == right ? right - 1 : right;
    while (i < j) {
      // Find the first element >= pivot
      while (i < right && elements[i] <= pivot) {
        i++;
      }

      // Find the last element < pivot
      while (j > left && elements[j] >= pivot) {
        j--;
      }

      // If the greater element is left of the lesser element, switch them
      if (i < j) {
        ArrayUtils.swap(elements, i, j);

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
    if (i != pivotPos) {
      // Is the pivotPos to i's left? --> go to the end of the left area
      if (pivotPos < i) {
        i--;
        if (i != pivotPos) {
          ArrayUtils.swap(elements, i, pivotPos);
        }
      } else {
        ArrayUtils.swap(elements, i, pivotPos);
      }
    }
    return i;
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

    int pivotPos = partitionWithCounters(elements, left, right, counters);
    quicksortWithCounters(elements, left, pivotPos - 1, counters);
    quicksortWithCounters(elements, pivotPos + 1, right, counters);
  }

  @Override
  public int partitionWithCounters(int[] elements, int left, int right, Counters counters) {
    int pivotPos = PivotHelper.findPivotPos(elements, left, right, pivotStrategy);
    int pivot = elements[pivotPos];

    int i = left;
    int j = pivotPos == right ? right - 1 : right;
    while (i < j) {
      counters.incIterations();

      // Find the first element >= pivot
      while (true) {
        counters.incComparisons();
        counters.incReads();
        if (elements[i] < pivot) {
          i++;
        } else {
          break;
        }
      }

      // Find the last element < pivot
      while (true) {
        counters.incComparisons();
        counters.incReads();
        if (j > left && elements[j] >= pivot) {
          j--;
        } else {
          break;
        }
      }

      // If the greater element is left of the lesser element, switch them
      if (i < j) {
        ArrayUtils.swap(elements, i, j);
        counters.addReadsAndWrites(2);

        // Remember new pivot pos
        if (i == pivotPos) {
          pivotPos = j;
        } else if (j == pivotPos) {
          pivotPos = i;
        }

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
    counters.incComparisons();
    if (i != pivotPos) {
      // Is the pivotPos to i's left? --> go to the end of the left area
      counters.incComparisons();
      if (pivotPos < i) {
        i--;
        if (i != pivotPos) {
          ArrayUtils.swap(elements, i, pivotPos);
          counters.addReadsAndWrites(2);
        }
      } else {
        ArrayUtils.swap(elements, i, pivotPos);
        counters.addReadsAndWrites(2);
      }
    }
    return i;
  }
}
