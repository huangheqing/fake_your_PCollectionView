public class Pipeline {

  // Read from table
  PCollection<TableRow> rows = pipeline.apply(BigQueryIO.Read
      .named("read from google bigquery")
      .fromQuery("select * from [your table name]"))
      .setCoder(TableRowJsonCoder.of());

  // get value from row
  PCollection<String> extractValue1 = rows.apply(ParDo.named("extract value").of(new DoFn<TableRow, String>() {
    @Override
    public void processElement(ProcessContext c) throws Exception {
      c.output(c.element().get("[field_name1]").toString());
    }
  }));

  // get second value from row
  PCollection<String> extractValue2 = rows.apply(ParDo.named("extract value").of(new DoFn<TableRow, String>() {
    @Override
    public void processElement(ProcessContext c) throws Exception {
      c.output(c.element().get("[field_name2]").toString());
    }
  }));

  // make first value to be a view, will be used as a side input
  PCollectionView<String> extractValueView = extractValue1.apply(View.asSingleton());

  // a DoFn take in a side input
  extractValue2.apply(ParDo.named("DoFn with side input").withSideInputs(extractValueView).of());

  @VisibleForTesting
  class Processing extends DoFn<String, Void>() {

    private final PCollectionView<String> extractValueView;

    Processing(PCollectionView<String> extractValueView){
      this.extractValueView = extractValueView;
    }

    @Override
    public void processElement (ProcessContext c) throws Exception {
      // NoOp implementation here
    }
  }
}