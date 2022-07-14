package eu.happycoders.sort.method.radixsort;

import static eu.happycoders.sort.method.radixsort.RadixSortHelper.calculateDivisor;
import static eu.happycoders.sort.method.radixsort.RadixSortHelper.checkIfContainsNegatives;
import static eu.happycoders.sort.method.radixsort.RadixSortHelper.getNumberOfDigits;
import static eu.happycoders.sort.utils.ArrayUtils.getMaximum;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.NotImplementedException;

/**
 * Recursive MSD (most significant digit) Radix Sort implementation using arrays as buckets and with
 * a customizable base.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class RecursiveMsdRadixSortWithArraysAndCustomBase implements SortAlgorithm {

  private final int base;

  public RecursiveMsdRadixSortWithArraysAndCustomBase(int base) {
    this.base = base;
  }

  @Override
  public void sort(int[] elements) {
    checkIfContainsNegatives(elements);
    int max = getMaximum(elements);
    int numberOfDigits = getNumberOfDigits(max, base);

    sortByDigit(elements, numberOfDigits - 1);
  }

  private void sortByDigit(int[] elements, int digitIndex) {
    Bucket[] buckets = partition(elements, digitIndex);

    // If we haven't reached the last digit, sort the buckets by the next digit, recursively
    if (digitIndex > 0) {
      for (Bucket bucket : buckets) {
        if (bucket.needsToBeSorted()) {
          sortByDigit(bucket.getElements(), digitIndex - 1);
        }
      }
    }

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
    int divisor = calculateDivisor(digitIndex, base);
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
    int divisor = calculateDivisor(digitIndex, base);

    for (int element : elements) {
      int digit = element / divisor % base;
      buckets[digit].add(element);
    }
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

    private boolean needsToBeSorted() {
      return elements.length > 1;
    }
  }
}
