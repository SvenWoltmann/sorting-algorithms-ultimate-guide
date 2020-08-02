package eu.happycoders.sort.method.mergesort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;

/**
 * Natural merge sort implementation for performance tests.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class NaturalMergeSort implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    int numElements = elements.length;

    int[] tmp = new int[numElements];
    int[] starts = new int[numElements + 1];

    // Step 1: identify runs
    int runCount = 0;
    starts[0] = 0;
    for (int i = 1; i <= numElements; i++) {
      if (i == numElements || elements[i] < elements[i - 1]) {
        starts[++runCount] = i;
      }
    }

    // Step 2: merge runs, until only 1 run is left
    int[] from = elements;
    int[] to = tmp;

    while (runCount > 1) {
      int newRunCount = 0;

      // Merge two runs each
      for (int i = 0; i < runCount - 1; i += 2) {
        merge(from, to, starts[i], starts[i + 1], starts[i + 2]);
        starts[newRunCount++] = starts[i];
      }

      // Odd number of runs? Copy the last one
      if (runCount % 2 == 1) {
        int lastStart = starts[runCount - 1];
        System.arraycopy(from, lastStart, to, lastStart,
              numElements - lastStart);
        starts[newRunCount++] = lastStart;
      }

      // Prepare for next round...
      starts[newRunCount] = numElements;
      runCount = newRunCount;

      // Swap "from" and "to" arrays
      int[] help = from;
      from = to;
      to = help;
    }

    // If final run is not in "elements", copy it there
    if (from != elements) {
      System.arraycopy(from, 0, elements, 0, numElements);
    }
  }

  private void merge(int[] source, int[] target, int startLeft,
                     int startRight, int endRight) {
    int leftPos = startLeft;
    int rightPos = startRight;
    int targetPos = startLeft;

    // As long as both arrays contain elements...
    while (leftPos < startRight && rightPos < endRight) {
      // Which one is smaller?
      int leftValue = source[leftPos];
      int rightValue = source[rightPos];
      if (leftValue <= rightValue) {
        target[targetPos++] = leftValue;
        leftPos++;
      } else {
        target[targetPos++] = rightValue;
        rightPos++;
      }
    }
    // Copy the rest
    while (leftPos < startRight) {
      target[targetPos++] = source[leftPos++];
    }
    while (rightPos < endRight) {
      target[targetPos++] = source[rightPos++];
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    // Not implemented
  }

}
