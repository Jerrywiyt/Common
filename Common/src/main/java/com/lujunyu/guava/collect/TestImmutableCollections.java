package com.lujunyu.guava.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * 不可变集合
 *
 * <p>why
 *
 * <p>相比较JDK自带的不可变集合，Guava提供的有诸多好处：
 *
 * <p>1. 可以提供给不被信任的库使用
 *
 * <p>2. 线程安全
 *
 * <p>3. 由于其不可变的特性，可以节省时间和空间复杂度。
 *
 * <p>4. 可以用来当做常量来使用
 *
 * <p>需要注意的点：
 *
 * <p>1. 不可变集合不能使用null元素。
 *
 * <p>how
 *
 * <p>如何使用：
 *
 * <p>1. ImmutableXXX.of()
 *
 * <p>2. ImmutableXXX.copyOf()
 *
 * <p>3. 使用builder：ImmutableXXX.builder().addAll().add().build();
 *
 * <p>不可变集合充分考虑了时间和空间复杂度的问题，所以一些特殊方法实现上有着足够的优化:
 *
 * <p>1. copyOf：这个方法并不像本意那样直接拷贝，由于Guava集合都是不可变的，所以当调用这个方法时，如果
 *
 * <p>发现被拷贝对象也是不可变集合，那么直接引用其底层存储就好，不需要进行拷贝，因此极大提升了此方法的性能。
 *
 * <p>2. asList：所有的不可变集合都实现了这个方法，这个方法相当于其内存view，调用该方法不会发生任何拷贝。
 */
public class TestImmutableCollections {
  /** 三种构造方式。 */
  @Test
  public void testHowToUse() {
    ImmutableSet.of();
    ImmutableSet.copyOf(Lists.newArrayList());
    ImmutableSet.builder().addAll(Lists.newArrayList()).add("").build();
  }

  @Test
  public void test() {
    ImmutableList<Integer> immutableList = ImmutableList.of(1, 2, 3, 4);
    ImmutableSet<Integer> immutableSet = ImmutableSet.of(1, 2, 3, 4);
    ImmutableSet<Integer> immutableSet1 = ImmutableSet.copyOf(immutableList);
    ImmutableList<Integer> immutableList1 = immutableSet.asList();
  }
}
