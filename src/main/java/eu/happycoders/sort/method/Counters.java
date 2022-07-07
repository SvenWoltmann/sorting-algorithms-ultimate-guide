package eu.happycoders.sort.method;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Locale;

/**
 * Counter for sort operations.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings("PMD.TooManyMethods") // That's OK for this simple counter class
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

  public void addIterations(int increment) {
    iterations += increment;
  }

  public void incComparisons() {
    comparisons++;
  }

  public void addComparisons(int increment) {
    comparisons += increment;
  }

  public void incReads() {
    reads++;
  }

  public void addReads(int increment) {
    reads += increment;
  }

  public void incWrites() {
    writes++;
  }

  public void addWrites(int increment) {
    writes += increment;
  }

  public void incReadsAndWrites() {
    reads++;
    writes++;
  }

  public void addReadsAndWrites(int increment) {
    reads += increment;
    writes += increment;
  }

  public void incLocalVariableAssignments() {
    localVariableAssignments++;
  }

  /**
   * Returns a second set of counters (used by Heapsort to count operations of phase 2). Create the
   * second set if it doesn't exist yet.
   *
   * <p>Not thread-safe!
   *
   * @return the second set of counters
   */
  @SuppressFBWarnings("EI_EXPOSE_REP") // We're intentionally exposing the "phase2" object
  public Counters getPhase2() {
    if (phase2 == null) {
      phase2 = new Counters();
    }
    return phase2;
  }

  @Override
  public String toString() {
    String result =
        String.format(
            Locale.US,
            "iterations = %,11d, comparisons = %,11d, "
                + "reads = %,11d, writes = %,11d, var.assignments = %,11d",
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
