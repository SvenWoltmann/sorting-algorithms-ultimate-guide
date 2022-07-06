package eu.happycoders.sort.method.quicksort;

import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.happycoders.sort.method.quicksort.DualPivotQuicksort.PivotStrategy;
import eu.happycoders.sort.utils.ArrayUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.junit.jupiter.api.*;

public class DualPivotQuicksortPartitionTest {

  // ---- PivotStrategy.LEFT_RIGHT ----

  @RepeatedTest(100)
  public void partitionLEFT_RIGHT_randomArray_partitioned() {
    testPartitioning(PivotStrategy.LEFT_RIGHT, ArrayUtils::createRandomArray);
  }

  @Test
  public void partitionLEFT_RIGHT_sortedArray_partitioned() {
    testPartitioning(PivotStrategy.LEFT_RIGHT, ArrayUtils::createSortedArray);
  }

  @Test
  public void partitionLEFT_RIGHT_reversedArray_partitioned() {
    testPartitioning(PivotStrategy.LEFT_RIGHT, ArrayUtils::createReversedArray);
  }

  // ---- PivotStrategy.MIDDLES ----

  @RepeatedTest(100)
  public void partitionMIDDLES_randomArray_partitioned() {
    testPartitioning(PivotStrategy.THIRDS, ArrayUtils::createRandomArray);
  }

  @Test
  public void partitionMIDDLES_sortedArray_partitioned() {
    testPartitioning(PivotStrategy.THIRDS, ArrayUtils::createSortedArray);
  }

  @Test
  public void partitionMIDDLES_reversedArray_partitioned() {
    testPartitioning(PivotStrategy.THIRDS, ArrayUtils::createReversedArray);
  }

  // ---- private methods ----

  private void testPartitioning(
      PivotStrategy pivotStrategy, Function<Integer, int[]> arraySupplier) {
    int[] elements = arraySupplier.apply(randomSize());
    int[] pivotPos =
        new DualPivotQuicksort(pivotStrategy).partition(elements, 0, elements.length - 1);
    int pivotPos0 = pivotPos[0];
    int pivotPos1 = pivotPos[1];

    assertTrue(pivotPos0 >= 0);
    assertTrue(pivotPos0 < pivotPos1);
    assertTrue(pivotPos1 < elements.length);
    assertTrue(elements[pivotPos0] <= elements[pivotPos1]);

    // All elements before pivotPos0 must be <= elements[pivotPos0]
    for (int i = 0; i < pivotPos0; i++) {
      assertTrue(elements[i] <= elements[pivotPos0]);
    }
    // All elements between pivotPos0 and pivotPos1
    // must be >= elements[pivotPos0] and <= elements[pivotPos1]
    for (int i = pivotPos0 + 1; i < pivotPos1; i++) {
      assertTrue(elements[i] >= elements[pivotPos0]);
      assertTrue(elements[i] <= elements[pivotPos1]);
    }
    // All elements after pivotPos1 must be >= elements[pivotPos1]
    for (int i = pivotPos1 + 1; i < elements.length; i++) {
      assertTrue(elements[i] >= elements[pivotPos1]);
    }
  }

  private int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }
}
