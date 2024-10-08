package eu.happycoders.sort.method.quicksort;

import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.happycoders.sort.utils.ArrayUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.junit.jupiter.api.*;

class QuicksortVariant3PartitionTest {

  // Unfortunately, we cannot combine @RepeatedTest with @ParameterizedTest

  // ---- PivotStrategy.RANDOM ----

  @RepeatedTest(100)
  void partitionRandom_randomArray_partitioned() {
    testPartitioning(PivotStrategy.RANDOM, ArrayUtils::createRandomArray);
  }

  @RepeatedTest(100)
  void partitionRandom_sortedArray_partitioned() {
    testPartitioning(PivotStrategy.RANDOM, ArrayUtils::createSortedArray);
  }

  @RepeatedTest(100)
  void partitionRandom_reversedArray_partitioned() {
    testPartitioning(PivotStrategy.RANDOM, ArrayUtils::createReversedArray);
  }

  // ---- PivotStrategy.LEFT ----

  @RepeatedTest(100)
  void partitionLEFT_randomArray_partitioned() {
    testPartitioning(PivotStrategy.LEFT, ArrayUtils::createRandomArray);
  }

  @Test
  void partitionLEFT_sortedArray_partitioned() {
    testPartitioning(PivotStrategy.LEFT, ArrayUtils::createSortedArray);
  }

  @Test
  void partitionLEFT_reversedArray_partitioned() {
    testPartitioning(PivotStrategy.LEFT, ArrayUtils::createReversedArray);
  }

  // ---- PivotStrategy.RIGHT ----

  @RepeatedTest(100)
  void partitionRIGHT_randomArray_partitioned() {
    testPartitioning(PivotStrategy.RIGHT, ArrayUtils::createRandomArray);
  }

  @Test
  void partitionRIGHT_sortedArray_partitioned() {
    testPartitioning(PivotStrategy.RIGHT, ArrayUtils::createSortedArray);
  }

  @Test
  void partitionRIGHT_reversedArray_partitioned() {
    testPartitioning(PivotStrategy.RIGHT, ArrayUtils::createReversedArray);
  }

  // ---- PivotStrategy.MIDDLE ----

  @RepeatedTest(100)
  void partitionMIDDLE_randomArray_partitioned() {
    testPartitioning(PivotStrategy.MIDDLE, ArrayUtils::createRandomArray);
  }

  @Test
  void partitionMIDDLE_sortedArray_partitioned() {
    testPartitioning(PivotStrategy.MIDDLE, ArrayUtils::createSortedArray);
  }

  @Test
  void partitionMIDDLE_reversedArray_partitioned() {
    testPartitioning(PivotStrategy.MIDDLE, ArrayUtils::createReversedArray);
  }

  // ---- PivotStrategy.MEDIAN3 ----

  @RepeatedTest(100)
  void partitionMEDIAN3_randomArray_partitioned() {
    testPartitioning(PivotStrategy.MEDIAN3, ArrayUtils::createRandomArray);
  }

  @Test
  void partitionMEDIAN3_sortedArray_partitioned() {
    testPartitioning(PivotStrategy.MEDIAN3, ArrayUtils::createSortedArray);
  }

  @Test
  void partitionMEDIAN3_reversedArray_partitioned() {
    testPartitioning(PivotStrategy.MEDIAN3, ArrayUtils::createReversedArray);
  }

  // ---- private methods ----

  private void testPartitioning(PivotStrategy pivotMode, Function<Integer, int[]> arraySupplier) {
    int[] elements = arraySupplier.apply(randomSize());
    int pivotPos = new QuicksortVariant3(pivotMode).partition(elements, 0, elements.length - 1);

    assertTrue(pivotPos >= 0);
    assertTrue(pivotPos < elements.length);

    // All elements before pivotPos must be <= pivot element
    int pivotElement = elements[pivotPos];
    for (int i = 0; i < pivotPos; i++) {
      assertTrue(elements[i] <= pivotElement);
    }
    // All elements after pivotPos must be >= pivot element
    for (int i = pivotPos + 1; i < elements.length; i++) {
      assertTrue(elements[i] >= pivotElement);
    }
  }

  private int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }
}
