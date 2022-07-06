package eu.happycoders.sort.method;

import java.util.concurrent.ThreadLocalRandom;

public abstract class SortTestLargeArray extends SortTest {

  @Override
  protected int randomSize() {
    return ThreadLocalRandom.current().nextInt(2, 10_000);
  }
}
