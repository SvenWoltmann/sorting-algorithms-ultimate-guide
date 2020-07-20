package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.mergesort.*;

/**
 * Compares the three merge sort algorithms.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareMergeSorts extends DirectComparison {

  private static final int SIZE = 5_555_555; // ~840 ms for Merge Sort

  public static void main(String[] args) {
    SortAlgorithm[] algorithms = new SortAlgorithm[]{
          new MergeSort(),
          new MergeSort2(),
          new MergeSort3()
    };
    runTest(algorithms, SIZE);
  }

}
