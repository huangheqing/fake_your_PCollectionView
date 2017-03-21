package com.goodwater.ocean.pipelines.dataflow.leadgen;

/**
 * For Creating fake pcollectionview for testing.
 * Created by heqinghuang on 1/31/17.
 */

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.coders.Coder;
import com.google.cloud.dataflow.sdk.transforms.AppliedPTransform;
import com.google.cloud.dataflow.sdk.util.WindowedValue;
import com.google.cloud.dataflow.sdk.util.WindowingStrategy;
import com.google.cloud.dataflow.sdk.values.PCollectionView;
import com.google.cloud.dataflow.sdk.values.PValue;
import com.google.cloud.dataflow.sdk.values.TupleTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Fake a PCollection view without requiring a PCollection.
 *
 * @param <T>
 */
public class FakePCollectionView<T> implements PCollectionView<T> {

  public FakePCollectionView() {
  }

  private static final TupleTag<Iterable<WindowedValue<?>>> KEY = new TupleTag<>();

  @Override
  public TupleTag<Iterable<WindowedValue<?>>> getTagInternal() {
    return KEY;
  }

  @Override
  public WindowingStrategy<?, ?> getWindowingStrategyInternal() {
    return WindowingStrategy.globalDefault();
  }

  @Override
  public Coder<Iterable<WindowedValue<?>>> getCoderInternal() {
    return null;
  }

  @Override
  public Object fromIterableInternal(Iterable contents) {
    List<T> list = new ArrayList<>();
    for (Object content : contents) {
      WindowedValue windowedValue = (WindowedValue) content;
      list.add((T) windowedValue.getValue());
    }
    return list;
  }

  @Override
  public String getName() {
    return "FAKE_PCOLLECTION_VIEW";
  }

  @Override
  public AppliedPTransform<?, ?, ?> getProducingTransformInternal() {
    return null;
  }

  @Override
  public void finishSpecifying() {

  }

  @Override
  public Pipeline getPipeline() {
    return null;
  }

  @Override
  public Collection<? extends PValue> expand() {
    return null;
  }

  @Override
  public void recordAsOutput(AppliedPTransform<?, ?, ?> transform) {

  }

  @Override
  public void finishSpecifyingOutput() {

  }
}
