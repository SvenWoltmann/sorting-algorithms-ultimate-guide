package eu.happycoders.sort.method.radixsort;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

class RadixSortHelperTest {

  @Test
  void checkIfContainsNegativesThrowsAnExceptionIfInputContainsANegativeNumber() {
    int[] elements = {5, -1, 8};
    assertThrowsExactly(
        IllegalArgumentException.class, () -> RadixSortHelper.checkIfContainsNegatives(elements));
  }

  @Test
  void checkIfContainsNegativesDoesNotThrowAnExceptionIfInputContainsNoNegativeNumber() {
    int[] elements = {0, 1, 2};
    assertDoesNotThrow(() -> RadixSortHelper.checkIfContainsNegatives(elements));
  }
}
