package eu.happycoders.sort.method;

import java.util.Arrays;

/**
 * Adapter for testing <code>Arrays.sort</code>.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class JavaArraysSort implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    Arrays.sort(elements);
  }

  @Override
  public void sort(int[] elements, Counters counters) {
    // Not implemented
  }

  @Override
  public boolean supportsCounting() {
    return false;
  }

}
