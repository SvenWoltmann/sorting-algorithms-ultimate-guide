package eu.happycoders.sort.method.heapsort;

public class BottomUpBuildHeapAndHeapifyTest extends BuildHeapAndHeapifyTest {

  @Override
  protected Heapsort getSortAlgorithm() {
    return new BottomUpHeapsort();
  }

}
