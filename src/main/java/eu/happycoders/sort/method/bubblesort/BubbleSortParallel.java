package eu.happycoders.sort.method.bubblesort;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.utils.NotImplementedException;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract base class for parallel Bubble Sort implementations using partitions.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public abstract class BubbleSortParallel implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    AtomicInteger lastSwappedInRound = new AtomicInteger();

    int[] startPositions = partition(elements);
    int numThreads = startPositions.length - 1;

    Phaser phaser = new Phaser(numThreads);
    Thread[] threads = new Thread[numThreads];
    for (int i = 0; i < numThreads; i++) {
      int startPos = startPositions[i];
      int endPos = startPositions[i + 1];
      threads[i] = createThread(elements, startPos, endPos, lastSwappedInRound, phaser);
    }

    for (int i = 0; i < numThreads; i++) {
      threads[i].start();
    }

    for (int i = 0; i < numThreads; i++) {
      try {
        threads[i].join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Partitions the elements.
   *
   * @param elements the elements
   * @return an array of start positions; the array's length is one more than the number of
   *     partitions; the last element contains the length of the array.
   */
  private int[] partition(int[] elements) {
    int numPartitions = Math.min(Runtime.getRuntime().availableProcessors(), elements.length / 2);
    int remainingElements = elements.length;
    int remainingPartitions = numPartitions;
    int[] startPositions = new int[numPartitions + 1];
    for (int i = 0; i < numPartitions; i++) {
      int partitionSize = remainingElements / remainingPartitions;
      if (isOdd(partitionSize) && startPositions[i] + partitionSize < elements.length) {
        partitionSize++;
      }

      remainingElements -= partitionSize;
      remainingPartitions--;
      startPositions[i + 1] = startPositions[i] + partitionSize;
    }
    return startPositions;
  }

  private boolean isOdd(int number) {
    return number % 2 != 0;
  }

  private Thread createThread(
      int[] elements, int startPos, int endPos, AtomicInteger lastSwappedInRound, Phaser phaser) {
    return new Thread(
        () -> {
          for (int round = 1; ; round++) {
            phaser.arriveAndAwaitAdvance();

            boolean swapped = sortPartition(elements, startPos, endPos, false);

            phaser.arriveAndAwaitAdvance();

            swapped |= sortPartition(elements, startPos, endPos, true);
            if (swapped) {
              lastSwappedInRound.set(round);
            }

            phaser.arriveAndAwaitAdvance();

            if (lastSwappedInRound.get() < round) {
              break;
            }
          }
        });
  }

  /**
   * Sorts a partition of the elements.
   *
   * @param elements the elements
   * @param startPos the partition's start position within the elements
   * @param endPos the partition's end position within the elements
   * @param even whether it's the even or odd step of an iteration
   * @return whether any elements were swapped
   */
  abstract boolean sortPartition(int[] elements, int startPos, int endPos, boolean even);

  @Override
  public void sortWithCounters(int[] elements, Counters counters) {
    throw new NotImplementedException();
  }

  @Override
  public boolean supportsCounting() {
    return false;
  }
}
