package eu.happycoders.sort.method.radixsort;

final class ParallelRadixSortHelper {

  private ParallelRadixSortHelper() {}

  static Segment[] splitIntoSegments(int[] elements) {
    int processors = Runtime.getRuntime().availableProcessors();

    // Let's put at least 10 elements into a segment, otherwise the overhead would be very high
    int maxProcessors = Math.max(elements.length / 10, 1);
    if (processors > maxProcessors) {
      processors = maxProcessors;
    }

    Segment[] segments = new Segment[processors];
    for (int i = 0; i < processors; i++) {
      int start = i * elements.length / processors;
      int nextStart = (i + 1) * elements.length / processors;
      segments[i] = new Segment(start, nextStart);
    }

    return segments;
  }

  static class Segment {
    private final int start;
    private final int end;

    private final int[] bucketCounts = new int[10];
    private final int[] bucketWritePositions = new int[10];

    private Segment(int start, int end) {
      this.start = start;
      this.end = end;
    }

    int getStart() {
      return start;
    }

    int getEnd() {
      return end;
    }

    int getBucketCount(int bucketIndex) {
      return bucketCounts[bucketIndex];
    }

    void setCounts(int[] counts) {
      System.arraycopy(counts, 0, bucketCounts, 0, 10);
    }

    int getBucketWritePosition(int bucketIndex) {
      return bucketWritePositions[bucketIndex];
    }

    int getAndIncrementBucketWritePosition(int bucketIndex) {
      int position = bucketWritePositions[bucketIndex];
      bucketWritePositions[bucketIndex]++;
      return position;
    }

    void setBucketWritePosition(int bucketIndex, int position) {
      bucketWritePositions[bucketIndex] = position;
    }

    void resetBucketWritePositions() {
      for (int i = 0; i < 10; i++) {
        bucketWritePositions[i] = 0;
      }
    }
  }
}
