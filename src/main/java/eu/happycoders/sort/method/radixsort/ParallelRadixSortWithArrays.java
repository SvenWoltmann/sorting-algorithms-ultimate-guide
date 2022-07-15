package eu.happycoders.sort.method.radixsort;

import static eu.happycoders.sort.method.radixsort.ParallelRadixSortHelper.splitIntoSegments;
import static eu.happycoders.sort.method.radixsort.RadixSortHelper.calculateDivisor;
import static eu.happycoders.sort.method.radixsort.RadixSortHelper.checkIfContainsNegatives;
import static eu.happycoders.sort.method.radixsort.RadixSortHelper.getNumberOfDigits;
import static eu.happycoders.sort.utils.ArrayUtils.getMaximum;

import eu.happycoders.sort.method.Counters;
import eu.happycoders.sort.method.SortAlgorithm;
import eu.happycoders.sort.method.radixsort.ParallelRadixSortHelper.Segment;
import eu.happycoders.sort.utils.NotImplementedException;
import java.util.Arrays;

/**
 * Parallel radix Sort implementation using arrays as buckets.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings("PMD.TooManyStaticImports")
public class ParallelRadixSortWithArrays implements SortAlgorithm {

  @Override
  public void sort(int[] elements) {
    checkIfContainsNegatives(elements);
    int max = getMaximum(elements);
    int numberOfDigits = getNumberOfDigits(max);

    // 1. Divide the input into segments to be processed in parallel
    Segment[] segments = splitIntoSegments(elements);

    for (int digitIndex = 0; digitIndex < numberOfDigits; digitIndex++) {
      sortByDigit(elements, segments, digitIndex);
    }
  }

  private void sortByDigit(int[] elements, Segment[] segments, int digitIndex) {
    Bucket[] buckets = partition(elements, segments, digitIndex);
    collect(buckets, elements);
  }

  private Bucket[] partition(int[] elements, Segment[] segments, int digitIndex) {
    // 2. Calculate in parallel per segment how many elements have to be sorted into which buckets
    Arrays.stream(segments)
        .parallel()
        .forEach(
            segment -> {
              int[] counts = countDigits(elements, digitIndex, segment);
              segment.setCounts(counts);
            });

    // 3. Calculate
    // a) the total number of elements per bucket and
    // b) the write position for each segment-bucket combination
    int[] counts = new int[10];
    for (int segmentIndex = 0; segmentIndex < segments.length; segmentIndex++) {
      Segment segment = segments[segmentIndex];
      segment.resetBucketWritePositions();
      for (int bucketIndex = 0; bucketIndex < 10; bucketIndex++) {
        counts[bucketIndex] += segment.getBucketCount(bucketIndex);

        if (segmentIndex > 0) {
          int previousSegmentBucketWritePosition =
              segments[segmentIndex - 1].getBucketWritePosition(bucketIndex);
          int previousSegmentBucketCount = segments[segmentIndex - 1].getBucketCount(bucketIndex);
          segment.setBucketWritePosition(
              bucketIndex, previousSegmentBucketWritePosition + previousSegmentBucketCount);
        }
      }
    }

    Bucket[] buckets = createBuckets(counts);

    // 4. Distribute the elements of the segments in parallel to the buckets
    Arrays.stream(segments)
        .parallel()
        .forEach(segment -> distributeToBuckets(elements, segment, digitIndex, buckets));

    return buckets;
  }

  private int[] countDigits(int[] elements, int digitIndex, Segment segment) {
    int[] counts = new int[10];
    int divisor = calculateDivisor(digitIndex);

    for (int i = segment.getStart(); i < segment.getEnd(); i++) {
      int element = elements[i];
      int digit = element / divisor % 10;
      counts[digit]++;
    }

    return counts;
  }

  private Bucket[] createBuckets(int[] counts) {
    Bucket[] buckets = new Bucket[10];
    for (int i = 0; i < 10; i++) {
      buckets[i] = new Bucket(counts[i]);
    }
    return buckets;
  }

  private void distributeToBuckets(
      int[] elements, Segment segment, int digitIndex, Bucket[] buckets) {
    int divisor = calculateDivisor(digitIndex);

    for (int i = segment.getStart(); i < segment.getEnd(); i++) {
      int element = elements[i];
      int digit = element / divisor % 10;
      int indexWithinTheBucket = segment.getAndIncrementBucketWritePosition(digit);
      buckets[digit].set(indexWithinTheBucket, element);
    }
  }

  private void collect(Bucket[] buckets, int[] elements) {
    // 5. For each bucket, calculate the offset in the target array
    // (= prefix sum over counts)
    calculateTargetOffsets(buckets);

    // 6. Collect the elements from the buckets for each bucket in parallel
    Arrays.stream(buckets)
        .parallel()
        .forEach(
            bucket -> {
              int targetOffset = bucket.getTargetOffset();
              int targetIndex = 0;
              for (int element : bucket.getElements()) {
                elements[targetOffset + targetIndex] = element;
                targetIndex++;
              }
            });
  }

  private void calculateTargetOffsets(Bucket[] buckets) {
    for (int i = 0; i < 10; i++) {
      int targetOffset;
      if (i > 0) {
        Bucket previousBucket = buckets[i - 1];
        targetOffset = previousBucket.getTargetOffset() + previousBucket.getElements().length;
      } else {
        targetOffset = 0;
      }
      buckets[i].setTargetOffset(targetOffset);
    }
  }

  @Override
  public void sortWithCounters(int[] elements, Counters counters) {
    throw new NotImplementedException();
  }

  @Override
  public boolean supportsCounting() {
    return false;
  }

  private static class Bucket {
    private final int[] elements;
    private int targetOffset;

    private Bucket(int size) {
      elements = new int[size];
    }

    private void set(int index, int element) {
      elements[index] = element;
    }

    private int[] getElements() {
      return elements;
    }

    private int getTargetOffset() {
      return targetOffset;
    }

    private void setTargetOffset(int targetOffset) {
      this.targetOffset = targetOffset;
    }
  }
}
