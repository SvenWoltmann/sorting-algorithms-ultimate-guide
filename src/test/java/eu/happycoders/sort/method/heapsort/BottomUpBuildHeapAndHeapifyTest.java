package eu.happycoders.sort.method.heapsort;

class BottomUpBuildHeapAndHeapifyTest extends BuildHeapAndHeapifyTest {

  @Override
  protected Heapsort getSortAlgorithm() {
    return new BottomUpHeapsort();
  }
}
