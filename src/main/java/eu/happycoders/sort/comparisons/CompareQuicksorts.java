package eu.happycoders.sort.comparisons;

import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.quicksort.PivotStrategy;
import eu.happycoders.sort.method.quicksort.QuicksortSimple;
import eu.happycoders.sort.method.quicksort.QuicksortVariant1;
import eu.happycoders.sort.method.quicksort.QuicksortVariant2;
import eu.happycoders.sort.method.quicksort.QuicksortVariant3;
import java.util.ArrayList;
import java.util.List;

/**
 * Compares the various Quicksort algorithm variants.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CompareQuicksorts extends DirectComparison {

  private static final int SIZE = 5_555_555; // ~500 ms for Quicksort

  public static void main(String[] args) {
    List<SortAlgorithm> algorithms = new ArrayList<>();

    algorithms.add(new QuicksortSimple());

    algorithms.add(new QuicksortVariant1(PivotStrategy.RANDOM));
    algorithms.add(new QuicksortVariant1(PivotStrategy.RIGHT));
    algorithms.add(new QuicksortVariant1(PivotStrategy.MIDDLE));
    algorithms.add(new QuicksortVariant1(PivotStrategy.MEDIAN3));

    algorithms.add(new QuicksortVariant2(PivotStrategy.RANDOM));
    algorithms.add(new QuicksortVariant2(PivotStrategy.RIGHT));
    algorithms.add(new QuicksortVariant2(PivotStrategy.MIDDLE));
    algorithms.add(new QuicksortVariant2(PivotStrategy.MEDIAN3));

    algorithms.add(new QuicksortVariant3(PivotStrategy.RANDOM));
    algorithms.add(new QuicksortVariant3(PivotStrategy.RIGHT));
    algorithms.add(new QuicksortVariant3(PivotStrategy.MIDDLE));
    algorithms.add(new QuicksortVariant3(PivotStrategy.MEDIAN3));

    runTest(algorithms.toArray(SortAlgorithm[]::new), SIZE);
  }
}
