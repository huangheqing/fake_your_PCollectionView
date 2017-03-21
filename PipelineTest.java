public class PipelineTest {

  // We need to test the pipeline. There are two ways of testing a pipeline.
  public void testProcess() {
    // We want to test our last DoFn called Processing
    DoFnTester<String, Void> doFnTester = DoFnTester.of(Processing);

    List<String> lists = Lists.newArrayList();

    PCollectionView<String> reportsToUpdateView = new FakePCollectionView<>();

    doFnTester.withSideInputInGlobaleWindow(reportsToUpdateView, lists);

    doFnTester.processElement("");
  }

}