package com.lujunyu.guava.collect;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.HashMultiset;
import org.junit.Test;

/**
 * 此接口在原有的Set基础上增加了为每个元素进行计数的功能。
 *
 * <p>相当于Set<Map<K,Integer>>结构。
 */
public class TestMultiSet {
  /** 包含的实现。 */
  @Test
  public void test() {
    // HashMultiSet
    // LinkedHashMultiSet
    // ConcurrentHashMultiSet
    // TreeMultiSet
    // ImmutableMultiSet
  }

  @Test
  public void testAll() {
    HashMultiset<String> hashMultiset = HashMultiset.create();
    hashMultiset.add("1");
    hashMultiset.add("1");
    hashMultiset.add("1");
    hashMultiset.add("1");
    hashMultiset.add("1");
    hashMultiset.add("2", 1);
    // size方法返回总数。
    assertEquals(6, hashMultiset.size());
    // size返回元素的数量。
    assertEquals(2, hashMultiset.elementSet().size());
    // 返回指定元素的个数。
    assertEquals(5, hashMultiset.count("1"));
    System.out.println(hashMultiset);
  }
}
