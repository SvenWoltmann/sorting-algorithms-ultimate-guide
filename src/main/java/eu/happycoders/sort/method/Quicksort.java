package eu.happycoders.sort.method;

import eu.happycoders.sort.utils.ArrayUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Quicksort implementation for performance tests, supporting various pivot
 * strategies.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class Quicksort implements SortAlgorithm, PartitioningAlgorithm {

  public enum PivotStrategy {RANDOM, LEFT, RIGHT, MIDDLE, MEDIAN3}

  private final PivotStrategy pivotStrategy;

  public Quicksort(PivotStrategy pivotStrategy) {
    this.pivotStrategy = pivotStrategy;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName() + "(pivot: " + pivotStrategy + ")";
  }

  @Override
  public boolean isSuitableForSortedInput() {
    // Don't run tests for sorted input with pivot strategies LEFT or RIGHT.
    // This will go n x into recursion.
    return pivotStrategy != PivotStrategy.LEFT
          && pivotStrategy != PivotStrategy.RIGHT;
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
    findPivotAndMoveRight(elements, left, right);
    int pivot = elements[right];

    int i = left;
    int j = right - 1;
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
        i++;
        j--;
      }
    }

    // i == j means we haven't checked this index yet.
    // Move i right if necessary so that i marks the start of the right array.
    if (i == j && elements[i] < pivot) {
      i++;
    }

    // Move pivot element to final position
    if (elements[i] > pivot) {
      ArrayUtils.swap(elements, i, right);
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
    findPivotAndMoveRight(elements, left, right);
    int pivot = elements[right];

    int i = left;
    int j = right;
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

    // Move pivot element to final position
    counters.incReads();
    counters.incComparisons();
    if (elements[i] > pivot) {
      ArrayUtils.swap(elements, i, right);
      counters.addReadsAndWrites(2);
    }
    return i;
  }

  private void findPivotAndMoveRight(int[] elements, int left, int right) {
    if (pivotStrategy == PivotStrategy.RIGHT) {
      return;
    }

    int pivotPos = switch (pivotStrategy) {
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

    if (pivotPos != right)
      ArrayUtils.swap(elements, pivotPos, right);
  }

  private int getMedian3Pos(int[] elements, int left, int right) {
    int rl = right - left;
    if (rl < 2) {
      return right;
    }

    int middle = left + (rl) / 2;

    int l = elements[left];
    int m = elements[middle];
    int r = elements[right];

    if (l < r) {
      if (l >= m) return left;
      else if (r < m) return right;
    } else {
      if (l < m) return left;
      else if (r >= m) return right;
    }
    return middle;
  }

}
