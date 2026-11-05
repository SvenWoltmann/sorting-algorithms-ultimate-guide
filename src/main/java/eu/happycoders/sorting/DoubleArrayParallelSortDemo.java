package eu.happycoders.sorting;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class DoubleArrayParallelSortDemo {
  private static final int NUMBER_OF_ELEMENTS = 100_000_000;

  public static void main(String[] args) {
    for (int i = 0; i < 5; i++) {
      sortTest("sort", Arrays::sort);
      sortTest("parallelSort", Arrays::parallelSort);
    }
  }

  private static void sortTest(String methodName, Consumer<double[]> sortMethod) {
    double[] a = createRandomArray(NUMBER_OF_ELEMENTS);
    long time = System.currentTimeMillis();
    sortMethod.accept(a);
    time = System.currentTimeMillis() - time;
    System.out.println(methodName + "() took " + time + " ms");
  }

  private static double[] createRandomArray(int n) {
    ThreadLocalRandom current = ThreadLocalRandom.current();
    double[] a = new double[n];
    for (int i = 0; i < n; i++) {
      a[i] = current.nextDouble();
    }
    return a;
  }
}
