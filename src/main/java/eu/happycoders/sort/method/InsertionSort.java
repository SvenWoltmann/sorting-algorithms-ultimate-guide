package eu.happycoders.sort.method;

/**
 * Insertion Sort implementation for performance tests.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class InsertionSort implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    sort(elements, 0, elements.length);
  }

  public void sort(int[] elements, int fromIndex, int toIndex) {
    for (int i = fromIndex + 1; i < toIndex; i++) {
      int elementToSort = elements[i];

      // Move element to the left until it's at the right position
      int j = i;
      while (j > fromIndex && elementToSort < elements[j - 1]) {
        elements[j] = elements[j - 1];
        j--;
      }
      elements[j] = elementToSort;
    }
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    sort(elements, 0, elements.length, counters);
  }

  public void sort(int[] elements, int fromIndex, int toIndex,
                   Counters counters) {
    for (int i = fromIndex + 1; i < toIndex; i++) {
      counters.incIterations();

      int number = elements[i];
      counters.incReads();

      // Move element to the left until it's at the right position
      int j = i;
      while (j > fromIndex) {
        counters.incIterations();

        counters.incComparisons();
        if (!(number < elements[j - 1])) break;

        elements[j] = elements[j - 1];
        counters.incReads();
        counters.incWrites();
        j--;
      }
      elements[j] = number;
      counters.incWrites();
    }
  }

}
