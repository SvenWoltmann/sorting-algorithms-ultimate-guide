package eu.happycoders.sort.pivot;

import java.util.Locale;

/**
 * Scorecard for the {@link PivotComparator}, printing how many times the partitioning was a
 * specific ratio or better.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class PivotScorecard {

  private final String name;

  private int count;
  private int count60Percent;
  private int count67Percent;
  private int count75Percent;

  public PivotScorecard(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Adds the specified larger partition percentage to the scorecard.
   *
   * @param largerPartPercentage the percentage of the larger partition compared to the number of
   *     elements
   */
  public void add(double largerPartPercentage) {
    if (largerPartPercentage < 50.0) {
      throw new IllegalArgumentException("The larger partition can not be less than 50%");
    }
    count++;
    if (largerPartPercentage <= 60.0) {
      count60Percent++;
    }
    if (largerPartPercentage <= 66.66666666666667) {
      count67Percent++;
    }
    if (largerPartPercentage <= 75.0) {
      count75Percent++;
    }
  }

  /**
   * Prints how many times the partitioning was 1.5:1 or better, 2:1 or better, and 3:1 or better.
   *
   * @param longestNameLength the length to which the name is padded
   */
  @SuppressWarnings({"PMD.SystemPrintln", "java:S106"})
  public void printResult(int longestNameLength) {
    System.out.printf(
        Locale.US,
        "%-"
            + longestNameLength
            + "s -> "
            + "1.5:1 or better: %5.2f %%; 2:1 or better: %5.2f %%; "
            + "3:1 or better: %5.2f %%%n",
        name,
        count60Percent * 100.0 / count,
        count67Percent * 100.0 / count,
        count75Percent * 100.0 / count);
  }
}
