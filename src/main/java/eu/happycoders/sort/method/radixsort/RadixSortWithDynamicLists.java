package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.NotImplementedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Radix Sort implementation using dynamic lists as buckets.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class RadixSortWithDynamicLists implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    checkIfContainsNegatives(elements);
    int max = getMaximum(elements);
    int numberOfDigits = getNumberOfDigits(max);

    for (int digitIndex = 0; digitIndex < numberOfDigits; digitIndex++) {
      sortByDigit(elements, digitIndex);
    }
  }

  private void checkIfContainsNegatives(int[] elements) {
    for (int element : elements) {
      if (element < 0) {
        throw new IllegalArgumentException("Negative elements are not allowed");
      }
    }
  }

  private int getMaximum(int[] elements) {
    int max = 0;
    for (int element : elements) {
      if (element > max) {
        max = element;
      }
    }
    return max;
  }

  private int getNumberOfDigits(int number) {
    int numberOfDigits = 1;
    while (number >= 10) {
      number /= 10;
      numberOfDigits++;
    }
    return numberOfDigits;
  }

  private void sortByDigit(int[] elements, int digitIndex) {
    Bucket[] buckets = partition(elements, digitIndex);
    collect(buckets, elements);
  }

  private Bucket[] partition(int[] elements, int digitIndex) {
    Bucket[] buckets = createBuckets();
    distributeToBuckets(elements, digitIndex, buckets);
    return buckets;
  }

  private Bucket[] createBuckets() {
    Bucket[] buckets = new Bucket[10];
    for (int i = 0; i < 10; i++) {
      buckets[i] = new Bucket();
    }
    return buckets;
  }

  private void distributeToBuckets(int[] elements, int digitIndex, Bucket[] buckets) {
    int divisor = calculateDivisor(digitIndex);

    for (int element : elements) {
      int digit = element / divisor % 10;
      buckets[digit].add(element);
    }
  }

  private int calculateDivisor(int digitIndex) {
    int divisor = 1;
    for (int i = 0; i < digitIndex; i++) {
      divisor *= 10;
    }
    return divisor;
  }

  private void collect(Bucket[] buckets, int[] elements) {
    int targetIndex = 0;
    for (Bucket bucket : buckets) {
      for (int element : bucket.getElements()) {
        elements[targetIndex] = element;
        targetIndex++;
      }
    }
  }

  @Override
  public void sortWithCounters(int[] elements, Counters counters) {
    throw new NotImplementedException();
  }

  @Override
  public boolean supportsCounting() {
    return false;
  }

  private static class Bucket {
    private final List<Integer> elements = new ArrayList<>();

    private void add(int element) {
      elements.add(element);
    }

    private List<Integer> getElements() {
      return elements;
    }
  }
}
