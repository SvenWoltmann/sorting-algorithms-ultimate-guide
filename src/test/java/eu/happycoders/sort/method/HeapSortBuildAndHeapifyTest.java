package eu.happycoders.sort.method;

import eu.happycoders.sort.utils.ArrayUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeapSortBuildAndHeapifyTest {

  static Stream<Arguments> arrayProvider() {
    return Stream.of(
          Arguments.of((Object) new int[]{1}),

          Arguments.of((Object) new int[]{1, 1}),
          Arguments.of((Object) new int[]{1, 2}),
          Arguments.of((Object) new int[]{2, 1}),

          Arguments.of((Object) new int[]{0, 0, 0}),
          Arguments.of((Object) new int[]{0, 0, 1}),
          Arguments.of((Object) new int[]{0, 1, 1}),
          Arguments.of((Object) new int[]{0, 1, 2}),
          Arguments.of((Object) new int[]{1, 1, 2}),

          Arguments.of((Object) new int[]{0, 1, 0}),
          Arguments.of((Object) new int[]{0, 2, 1}),
          Arguments.of((Object) new int[]{1, 2, 1}),

          Arguments.of((Object) new int[]{1, 0, 0}),
          Arguments.of((Object) new int[]{1, 1, 0}),
          Arguments.of((Object) new int[]{2, 1, 0}),
          Arguments.of((Object) new int[]{2, 1, 1})
    );
  }

  @ParameterizedTest
  @MethodSource("arrayProvider")
  public void build_givenArray_isMaxHeap(int[] array) {
    testArray(array);
  }

  @RepeatedTest(100)
  public void build_randomArray_isMaxHeap() {
    int size = ThreadLocalRandom.current().nextInt(4, 100);
    int[] array = ArrayUtils.createRandomArray(size);
    testArray(array);
  }

  private void testArray(int[] array) {
    new HeapSort().buildHeap(array);
    assertIsHeap(array);
  }

  private void assertIsHeap(int[] array) {
    // Every element must be greater than or equal to its childs
    for (int i = 0; i < array.length / 2; i++) {
      int parent = array[i];

      int leftChildPos = i * 2 + 1;
      if (leftChildPos < array.length) {
        assertTrue(parent >= array[leftChildPos]);
      }

      int rightChildPos = i * 2 + 2;
      if (rightChildPos < array.length) {
        assertTrue(parent >= array[rightChildPos]);
      }
    }
  }

}
