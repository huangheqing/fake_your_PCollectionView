# fake_your_PCollectionView

I would like share my experience on how to test Google dataflow pipeline with PCollection and PCollectionView.

Even though AWS is dominating the market, but Google Cloud Platform is getting larger. When we are using Google Cloud API
to interact with Google BigQuery, App engine, etc; we will definitely experiencing difficulties on testing our pipelines.

Below is the official document from Google on how to test on your pipeline.
[how to test your pipeline doc from Google](https://cloud.google.com/dataflow/pipelines/testing-your-pipeline)

But let me tell you, this document is extremely bad for beginners. Let me show you some examples from the document:

````
static class MyDoFn extends DoFn<String, Integer> { ... }
  MyDoFn myDoFn = ...;
  DoFnTester<String, Integer> fnTester = DoFnTester.of(myDoFn);

  PCollectionView<List<Integer>> sideInput = ...;
  Iterable<Integer> value = ...;
  fnTester.setSideInputInGlobalWindow(sideInput, value);
````

WTF is "..."? Google, Please tell me. With this piece of code for a beginner, no one could have realized how to handle PCollectionView.

So, in this REPO, I will share my code on how to fake out a PCollectionView and test your pipeline.

The reason we need to fake out the PCollectionView is because for some of the situation, we don't want to run the entire pipeline from beginning. We only want to test one of the DoFn.
But PCollectionView can only be created with a pipeline running, for example in real situation, we need to create PCollectionView like this:

````
PCollectionView<String> view = pipeline.apply(ParDo.of(new DoFn)).apply(View.asSingleton);
````

But what if we don't have a pipeline at this point. So in my example code PipelineTest.java. I used the fake object creted earlier.

````
PCollectionView<String> reportsToUpdateView = new FakePCollectionView<>();
````

With the fake, you can basically do anything with it. Give any types to it.
If you want more features from the fake object, you can override all of the methods from it's parent.



