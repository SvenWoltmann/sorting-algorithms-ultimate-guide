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
  private long localVariableAssignments;

  private Counters phase2;

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

  public void incLocalVariableAssignments() {
    localVariableAssignments++;
  }

  /**
   * Returns a second set of counters (used by Heapsort to count operations
   * of phase 2). Create the second set if it doesn't exist yet.
   *
   * <p>
   * Not thread-safe!
   *
   * @return
   */
  public Counters getPhase2() {
    if (phase2 == null) {
      phase2 = new Counters();
    }
    return phase2;
  }

  @Override
  public String toString() {
    String result = String.format(Locale.US,
          "iterations = %,11d, comparisons = %,11d, " +
                "reads = %,11d, writes = %,11d, var.assignments = %,11d",
          iterations,
          comparisons,
          reads,
          writes,
          localVariableAssignments);
    if (phase2 != null) {
      result += "; Phase2: " + phase2.toString();
    }
    return result;
  }

}
