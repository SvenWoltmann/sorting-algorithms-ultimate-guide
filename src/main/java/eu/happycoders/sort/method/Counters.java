package eu.happycoders.sort.method;

import java.util.Locale;

/**
 * Counter for sort operations.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class Counters {

  private long iterations;
  private long comparisons;
  private long reads;
  private long writes;

  public void incIterations() {
    iterations++;
  }

  public void addIterations(int x) {
    iterations += x;
  }

  public void incComparisons() {
    comparisons++;
  }

  public void addComparisons(int x) {
    comparisons += x;
  }

  public void incReads() {
    reads++;
  }

  public void addReads(int x) {
    reads += x;
  }

  public void incWrites() {
    writes++;
  }

  public void addWrites(int x) {
    writes += x;
  }

  public void incReadsAndWrites() {
    reads++;
    writes++;
  }

  public void addReadsAndWrites(int x) {
    reads += x;
    writes += x;
  }

  @Override
  public String toString() {
    return String.format(Locale.US,
          "iterations = %,11d, comparisons = %,11d, " +
                "reads = %,11d, writes = %,11d",
          iterations,
          comparisons,
          reads,
          writes);
  }

}
