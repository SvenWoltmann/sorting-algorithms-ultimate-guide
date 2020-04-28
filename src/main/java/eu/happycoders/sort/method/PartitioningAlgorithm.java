package eu.happycoders.sort.method;

/**
 * Partitioning algorithm interface; having all Quicksort algorithms
 * implement this interface makes it easier to compare the performance of
 * their partitioning algorithms.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface PartitioningAlgorithm extends SortAlgorithm {

  /**
   * Partitions the given elements from the left to the right position and
   * returns the position of the pivot element.
   *
   * @param elements the elements to partition
   * @param left     the left position   in the index
   * @param right    the right position in the index
   * @return the position of the pivot element
   */
  int partition(int[] elements, int left, int right);

}
