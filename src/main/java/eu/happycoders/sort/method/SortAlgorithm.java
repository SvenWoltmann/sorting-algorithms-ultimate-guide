package eu.happycoders.sort.method;

/**
 * Sort algorithm interface; having all sort algorithms implement this interface makes it easier to
 * write a test program.
 *
 * <p>If not for a test, I would omit the interface and make the sort methods static instead.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface SortAlgorithm {

  void sort(int[] elements);

  void sortWithCounters(int[] elements, Counters counters);

  /**
   * Returns the name, which is the class name by default, but can also be overridden, e.g., in
   * Quicksort to include the pivot strategy.
   *
   * @return the name
   */
  default String getName() {
    return this.getClass().getSimpleName();
  }

  /**
   * Indicates whether this test is suitable for pre-sorted input.
   *
   * <p>This is, for example, not the case for Quicksort using the left-most or right-most element
   * as pivot element, as the recursion would be too deep and we would get a StackOverflowException.
   *
   * @param size the number of elements
   * @return whether this test is suitable for pre-sorted input
   */
  default boolean isSuitableForSortedInput(int size) {
    return true;
  }

  /**
   * Indicates whether this test is suitable for the given input size.
   *
   * <p>CountingSort, for example, should be limited to a specific size.
   *
   * @param size the number of elements
   * @return whether this test is suitable for pre-sorted input
   */
  default boolean isSuitableForInputSize(int size) {
    return true;
  }

  /**
   * Indicates whether this algorithm supports counting operations.
   *
   * @return whether this algorithm supports counting operations
   */
  default boolean supportsCounting() {
    return true;
  }
}
