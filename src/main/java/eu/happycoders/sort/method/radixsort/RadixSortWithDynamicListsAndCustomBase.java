package eu.happycoders.sort.method.radixsort;

import static eu.happycoders.sort.method.radixsort.RadixSortHelper.calculateDivisor;
import static eu.happycoders.sort.method.radixsort.RadixSortHelper.checkIfContainsNegatives;
import static eu.happycoders.sort.method.radixsort.RadixSortHelper.getNumberOfDigits;
import static eu.happycoders.sort.utils.ArrayUtils.getMaximum;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.NotImplementedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Radix Sort implementation using dynamic lists as buckets and with a customizable base.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class RadixSortWithDynamicListsAndCustomBase implements SortAlgorithm {

  private final int base;

  public RadixSortWithDynamicListsAndCustomBase(int base) {
    this.base = base;
  }

  @Override
  public void sort(int[] elements) {
    checkIfContainsNegatives(elements);
    int max = getMaximum(elements);
    int numberOfDigits = getNumberOfDigits(max, base);

    for (int digitIndex = 0; digitIndex < numberOfDigits; digitIndex++) {
      sortByDigit(elements, digitIndex);
    }
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
    Bucket[] buckets = new Bucket[base];
    for (int i = 0; i < base; i++) {
      buckets[i] = new Bucket();
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
    private final List<Integer> elements = new ArrayList<>();

    private void add(int element) {
      elements.add(element);
    }

    private List<Integer> getElements() {
      return elements;
    }
  }
}
