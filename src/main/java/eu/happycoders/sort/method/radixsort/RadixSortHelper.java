package eu.happycoders.sort.method.radixsort;

final class RadixSortHelper {

  private RadixSortHelper() {}

  static void checkIfContainsNegatives(int[] elements) {
    for (int element : elements) {
      if (element < 0) {
        throw new IllegalArgumentException("Negative elements are not allowed");
      }
    }
  }

  static int getNumberOfDigits(int number) {
    int numberOfDigits = 1;
    while (number >= 10) {
      number /= 10;
      numberOfDigits++;
    }
    return numberOfDigits;
  }

  static int getNumberOfDigits(int number, int base) {
    int numberOfDigits = 1;
    while (number >= base) {
      number /= base;
      numberOfDigits++;
    }
    return numberOfDigits;
  }

  static int calculateDivisor(int digitIndex) {
    int divisor = 1;
    for (int i = 0; i < digitIndex; i++) {
      divisor *= 10;
    }
    return divisor;
  }

  static int calculateDivisor(int digitIndex, int base) {
    int divisor = 1;
    for (int i = 0; i < digitIndex; i++) {
      divisor *= base;
    }
    return divisor;
  }
}
