package eu.happycoders.sort.utils;

import eu.happycoders.sort.method.SortAlgorithm;

import java.util.*;

/**
 * A scorecard to print the fastest and median times measured for a specific
 * sort algorithm and array size.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class Scorecard {

  private final String name;
  private final ArrayList<Long> times = new ArrayList<>();

  private long fastest = Long.MAX_VALUE;

  public Scorecard(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Adds a time to the scorecard.
   *
   * @param time the time
   * @return <code>true</code> if the time is a new record;
   * <code>false</code> otherwise
   */
  public boolean add(long time) {
    times.add(time);
    if (time < fastest) {
      fastest = time;
      return true;
    }
    return false;
  }

  /**
   * Prints the fastest and median times; followed by an optional label.
   *
   * @param longestNameLength the length to which the name is padded
   * @param label             the optional label
   */
  public void printResult(int longestNameLength, String label) {
    System.out.printf(Locale.US, "%-" + longestNameLength + "s -> " +
                "fastest: %,10.3f ms, median: %,10.3f ms %s%n",
          name,
          fastest / 1_000_000.0,
          getMedian() / 1_000_000.0,
          label != null ? label : "");
  }

  /**
   * Returns the median time.
   *
   * @return the median time
   */
  public long getMedian() {
    int len = times.size();
    long[] array = new long[len];
    for (int i = 0; i < len; i++) {
      array[i] = times.get(i);
    }
    return ArrayUtils.median(array);
  }

  public static int findLongestAlgorithmName(SortAlgorithm[] algorithms) {
    int max = 0;
    for (SortAlgorithm algorithm : algorithms) {
      int nameLength = algorithm.getName().length();
      if (nameLength > max) {
        max = nameLength;
      }
    }
    return max;
  }

}
