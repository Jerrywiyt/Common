package com.lujunyu.guava.collect;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import org.junit.Test;

public class TestOthers {

  /**
   * 目前没有搞清楚这个有啥用。
   */
  @Test
  public void testClassToInstanceMap(){
    MutableClassToInstanceMap<String> objectMutableClassToInstanceMap = MutableClassToInstanceMap.create();
    objectMutableClassToInstanceMap.putInstance(String.class,"bj");
    System.out.println(objectMutableClassToInstanceMap.getInstance(String.class));
  }

  /**
   * 这个数据结构用于表示一段区间范围。
   */
  @Test
  public void testRangeSet(){
    ImmutableRangeSet<Integer> rangeSet = ImmutableRangeSet.<Integer>builder().add(Range.closed(1, 2))
        .add(Range.open(2, 10))
        .build();

    System.out.println(rangeSet.contains(3));
    System.out.println(rangeSet.contains(11));
    System.out.println(rangeSet.complement().contains(-1));
    System.out.println(rangeSet);
  }

  /**
   * 用于表示一段区间映射的值。
   */
  @Test
  public void testRangeMap(){
    ImmutableRangeMap<Integer, String> rangeMap = ImmutableRangeMap.<Integer,String>builder().put(Range.closed(1, 2), "1-2").put(Range.closed(3, 5), "3-5").build();
    System.out.println(rangeMap.get(1));
    System.out.println(rangeMap.get(2));
    System.out.println(rangeMap.get(3));
    System.out.println(rangeMap.get(10));
    System.out.println(rangeMap);
  }
}
