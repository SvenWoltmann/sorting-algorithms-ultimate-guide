package eu.happycoders.sort.demos.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.radixsort.RadixSortWithArrays;
import eu.happycoders.sort.method.radixsort.RadixSortWithArraysAndCustomBase;
import eu.happycoders.sort.method.radixsort.RadixSortWithCountingSort;
import eu.happycoders.sort.method.radixsort.RadixSortWithCountingSortAndCustomBase;
import eu.happycoders.sort.method.radixsort.RadixSortWithDynamicLists;
import eu.happycoders.sort.method.radixsort.RadixSortWithDynamicListsAndCustomBase;
import eu.happycoders.sort.method.radixsort.RecursiveMsdRadixSortWithArrays;
import eu.happycoders.sort.method.radixsort.RecursiveMsdRadixSortWithArraysAndCustomBase;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Compares the various Radix Sort algorithm variants.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareRadixSorts extends DirectComparison {

  private static final int SIZE = 5_555_555;
  private static final int MAX_BASE = 1 << 20;

  public static void main(String[] args) {
    new CompareRadixSorts().run();
  }

  private void run() {
    List<SortAlgorithm> algorithms = new ArrayList<>();

    algorithms.add(new RadixSortWithDynamicLists());
    algorithms.addAll(createWithVariousBases(RadixSortWithDynamicListsAndCustomBase::new));

    algorithms.add(new RadixSortWithArrays());
    algorithms.addAll(createWithVariousBases(RadixSortWithArraysAndCustomBase::new));

    algorithms.add(new RadixSortWithCountingSort());
    algorithms.addAll(createWithVariousBases(RadixSortWithCountingSortAndCustomBase::new));

    algorithms.add(new RecursiveMsdRadixSortWithArrays());
    algorithms.addAll(createWithVariousBases(RecursiveMsdRadixSortWithArraysAndCustomBase::new));

    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }

  private List<SortAlgorithm> createWithVariousBases(
      IntFunction<SortAlgorithm> algorithmConstructor) {
    List<SortAlgorithm> algorithms = new ArrayList<>();

    int next10Base = 10;

    for (int base = 2; base <= MAX_BASE; base *= 2) {
      if (base > next10Base) {
        algorithms.add(algorithmConstructor.apply(next10Base));
        next10Base *= 10;
      }

      algorithms.add(algorithmConstructor.apply(base));
    }

    return algorithms;
  }
}
