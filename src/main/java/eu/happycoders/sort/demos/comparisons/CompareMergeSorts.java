package eu.happycoders.sort.demos.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.mergesort.MergeSort;
import eu.happycoders.sort.method.mergesort.MergeSort2;
import eu.happycoders.sort.method.mergesort.MergeSort3;

/**
 * Compares the three merge sort algorithms.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareMergeSorts extends DirectComparison {

  private static final int SIZE = 4_444_444; // ~600 ms for Merge Sort

  public static void main(String[] args) {
    new CompareMergeSorts().run();
  }

  private void run() {
    SortAlgorithm[] algorithms = {new MergeSort(), new MergeSort2(), new MergeSort3()};

    runTest(algorithms, SIZE);
  }
}
