package eu.happycoders.sort.method.quicksort;

import eu.happycoders.sort.method.Counters;

/**
 * Quicksort implementation for performance tests, supporting various pivot
 * strategies.
 *
 * <p>
 * Variant 1: swaps pivot element with rightmost element first
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class QuicksortVariant1 extends QuicksortSimple {

  private final PivotStrategy pivotStrategy;

  public QuicksortVariant1(PivotStrategy pivotStrategy) {
    this.pivotStrategy = pivotStrategy;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName() + "(pivot: " + pivotStrategy + ")";
  }

  @Override
  public boolean isSuitableForSortedInput(int size) {
    return (pivotStrategy != PivotStrategy.LEFT
          && pivotStrategy != PivotStrategy.RIGHT) || size <= 2 << 12;
  }

  @Override
  public int partition(int[] elements, int left, int right) {
    PivotHelper.findPivotAndMoveRight(elements, left, right, pivotStrategy);
    return super.partition(elements, left, right);
  }

  public int partition(int[] elements, int left, int right, Counters counters) {
    PivotHelper.findPivotAndMoveRight(elements, left, right, pivotStrategy);
    return super.partition(elements, left, right, counters);
  }

}
