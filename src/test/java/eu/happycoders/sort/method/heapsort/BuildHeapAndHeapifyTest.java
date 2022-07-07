package eu.happycoders.sort.method.heapsort;

import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.happycoders.sort.utils.ArrayUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public abstract class BuildHeapAndHeapifyTest {

  static Stream<Arguments> arrayProvider() {
    return Stream.of(
        Arguments.of(new int[] {1}),
        Arguments.of(new int[] {1, 1}),
        Arguments.of(new int[] {1, 2}),
        Arguments.of(new int[] {2, 1}),
        Arguments.of(new int[] {0, 0, 0}),
        Arguments.of(new int[] {0, 0, 1}),
        Arguments.of(new int[] {0, 1, 1}),
        Arguments.of(new int[] {0, 1, 2}),
        Arguments.of(new int[] {1, 1, 2}),
        Arguments.of(new int[] {0, 1, 0}),
        Arguments.of(new int[] {0, 2, 1}),
        Arguments.of(new int[] {1, 2, 1}),
        Arguments.of(new int[] {1, 0, 0}),
        Arguments.of(new int[] {1, 1, 0}),
        Arguments.of(new int[] {2, 1, 0}),
        Arguments.of(new int[] {2, 1, 1}));
  }

  @ParameterizedTest
  @MethodSource("arrayProvider")
  void build_givenArray_isMaxHeap(int[] array) {
    testArray(array);
  }

  @RepeatedTest(100)
  void build_randomArray_isMaxHeap() {
    int size = ThreadLocalRandom.current().nextInt(4, 100);
    int[] array = ArrayUtils.createRandomArray(size);
    testArray(array);
  }

  private void testArray(int[] array) {
    getSortAlgorithm().buildHeap(array);
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

  protected abstract Heapsort getSortAlgorithm();
}
