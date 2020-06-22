package eu.happycoders.sort;

import eu.happycoders.sort.utils.ArrayUtils;

import java.util.Locale;

/**
 * Counts minPos/min assignments for finding the smallest element in an
 * unsorted array.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class FindMinimumTest {
  private static final int NUM_SIZES = 29;
  private static final int NUM_TESTS = 100;
  private static final int[] counts = new int[NUM_SIZES];
  private static final int[][] countsM = new int[NUM_SIZES][NUM_TESTS];

  public static void main(String[] args) {
    for (int i = 0; i < NUM_TESTS; i++) {
      test(i);
      printResults(i + 1);
    }
  }

  private static void test(int iteration) {
    for (int i = 0; i < NUM_SIZES; i++) {
      int size = 2 << i;
      int assignments = countAssignmentsForSize(size);
      counts[i] += assignments;
      countsM[i][iteration] = assignments;
    }
  }

  private static int countAssignmentsForSize(int size) {
    int[] array = ArrayUtils.createRandomArray(size);
    int min = Integer.MAX_VALUE;
    int assignments = 0;
    for (int i = 0; i < size; i++) {
      int element = array[i];
      if (element < min) {
        min = element;
        assignments++;
      }
    }
    return assignments;
  }

  private static void printResults(int iterations) {
    System.out.printf(Locale.US, "Results after %d iterations:%n", iterations);
    for (int i = 0; i < NUM_SIZES; i++) {
      int size = 2 << i;
      double avg = (double) counts[i] / iterations;
      System.out.printf(Locale.US,
            "- size: %,11d --> avg. no of assignments: %5.2f%n", size, avg);
    }
    System.out.println();
  }

  private static double median(int[] count, int iterations) {
    long[] longs = new long[iterations];
    for (int i = 0; i < iterations; i++) {
      longs[i] = count[i] * 10;
    }
    long median = ArrayUtils.median(longs);
    return median / 10.0;
  }
}
