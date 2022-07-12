package eu.happycoders.sort.method.radixsort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.NotImplementedException;

/**
 * Radix Sort implementation using counting sort and with a customizable base.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class RadixSortWithCountingSortAndCustomBase implements SortAlgorithm {

  private final int base;

  public RadixSortWithCountingSortAndCustomBase(int base) {
    this.base = base;
  }

  @Override
  public void sort(int[] elements) {
    checkIfContainsNegatives(elements);
    int max = getMaximum(elements);
    int numberOfDigits = getNumberOfDigits(max);

    // Remember input array
    int[] inputArray = elements;

    for (int digitIndex = 0; digitIndex < numberOfDigits; digitIndex++) {
      elements = sortByDigit(elements, digitIndex);
    }

    // Copy sorted elements back to input array
    System.arraycopy(elements, 0, inputArray, 0, elements.length);
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

  private int[] sortByDigit(int[] elements, int digitIndex) {
    int[] counts = countDigits(elements, digitIndex);
    int[] prefixSums = calculatePrefixSums(counts);
    return collectElements(elements, digitIndex, prefixSums);
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

  private int[] calculatePrefixSums(int[] counts) {
    int[] prefixSums = new int[base];
    prefixSums[0] = counts[0];
    for (int i = 1; i < base; i++) {
      prefixSums[i] = prefixSums[i - 1] + counts[i];
    }
    return prefixSums;
  }

  private int[] collectElements(int[] elements, int digitIndex, int[] prefixSums) {
    int divisor = calculateDivisor(digitIndex);
    int[] target = new int[elements.length];
    for (int i = elements.length - 1; i >= 0; i--) {
      int element = elements[i];
      int digit = element / divisor % base;
      target[--prefixSums[digit]] = element;
    }
    return target;
  }

  private int calculateDivisor(int digitIndex) {
    int divisor = 1;
    for (int i = 0; i < digitIndex; i++) {
      divisor *= base;
    }
    return divisor;
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
}
