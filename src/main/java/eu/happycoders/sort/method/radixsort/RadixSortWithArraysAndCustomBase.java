package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.NotImplementedException;

/**
 * Radix Sort implementation using arrays as buckets and with a customizable base.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class RadixSortWithArraysAndCustomBase implements SortAlgorithm {

  private final int base;

  public RadixSortWithArraysAndCustomBase(int base) {
    this.base = base;
  }

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
    while (number >= base) {
      number /= base;
      numberOfDigits++;
    }
    return numberOfDigits;
  }

  private void sortByDigit(int[] elements, int digitIndex) {
    Bucket[] buckets = partition(elements, digitIndex);
    collect(buckets, elements);
  }

  private Bucket[] partition(int[] elements, int digitIndex) {
    int[] counts = countDigits(elements, digitIndex);
    Bucket[] buckets = createBuckets(counts);
    distributeToBuckets(elements, digitIndex, buckets);
    return buckets;
  }

  private int[] countDigits(int[] elements, int digitIndex) {
    int[] counts = new int[base];
    int divisor = calculateDivisor(digitIndex);
    for (int element : elements) {
      int digit = element / divisor % base;
      counts[digit]++;
    }
    return counts;
  }

  private Bucket[] createBuckets(int[] counts) {
    Bucket[] buckets = new Bucket[base];
    for (int i = 0; i < base; i++) {
      buckets[i] = new Bucket(counts[i]);
    }
    return buckets;
  }

  private void distributeToBuckets(int[] elements, int digitIndex, Bucket[] buckets) {
    int divisor = calculateDivisor(digitIndex);

    for (int element : elements) {
      int digit = element / divisor % base;
      buckets[digit].add(element);
    }
  }

  private int calculateDivisor(int digitIndex) {
    int divisor = 1;
    for (int i = 0; i < digitIndex; i++) {
      divisor *= base;
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
  public String getName() {
    return this.getClass().getSimpleName() + "(" + base + ")";
  }

  @Override
  public boolean supportsCounting() {
    return false;
  }

  private static class Bucket {
    private final int[] elements;
    private int addIndex;

    private Bucket(int size) {
      elements = new int[size];
    }

    private void add(int element) {
      elements[addIndex] = element;
      addIndex++;
    }

    private int[] getElements() {
      return elements;
    }
  }
}
