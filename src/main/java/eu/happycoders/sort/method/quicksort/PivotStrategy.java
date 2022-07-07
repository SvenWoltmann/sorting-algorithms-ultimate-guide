package eu.happycoders.sort.method.quicksort;

/**
 * Strategy for calculating the pivot position.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public enum PivotStrategy {
  RANDOM,
  LEFT,
  RIGHT,
  MIDDLE,
  MEDIAN3
}
