package eu.happycoders.sort.method;

/**
 * Selection Sort implementation for performance tests.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class SelectionSort implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    int length = elements.length;

    for (int i = 0; i < length - 1; i++) {
      // Search the smallest element in the remaining array
      int minPos = i;
      int min = elements[minPos];
      for (int j = i + 1; j < length; j++) {
        if (elements[j] < min) {
          minPos = j;
          min = elements[minPos];
        }
      }

      // Swap min with element at pos i
      if (minPos != i) {
        elements[minPos] = elements[i];
        elements[i] = min;
      }
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    int length = elements.length;

    for (int i = 0; i < length - 1; i++) {
      // Search the smallest element in the remaining array
      counters.incIterations();

      int minPos = i;
      int min = elements[minPos];
      counters.incReads();
      counters.incLocalVariableAssignments();

      for (int j = i + 1; j < length; j++) {
        counters.incIterations();

        int numAtJ = elements[j];
        counters.incReads();

        counters.incComparisons();
        if (numAtJ < min) {
          minPos = j;
          min = numAtJ;
          counters.incLocalVariableAssignments();
        }
      }

      // Swap min with element at pos i
      if (minPos != i) {
        elements[minPos] = elements[i];
        counters.incReads();

        elements[i] = min;
        counters.incWrites();
      }
    }
  }

}
