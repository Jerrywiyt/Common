package com.lujunyu.guava.collect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeMultimap;
import org.junit.Test;

/**
 * 结构类似： Map<K,List<V>> , Map<K,Set<V>>
 *
 * <p>在很多场景中都会用到类似的结构，此集合同时还提供了多种操作方法。
 */
public class TestMultiMap {

  @Test
  public void testHashMap() {
    HashMultimap<String, String> hashMultimap = HashMultimap.create();
    hashMultimap.put("1", "1");
    hashMultimap.put("1", "1");
    hashMultimap.put("1", "1");
    hashMultimap.put("1", "1");
    hashMultimap.put("2", "1");
    System.out.println(hashMultimap);
  }

  @Test
  public void testImmutable() {
    ImmutableListMultimap<Integer, Integer> listMultimap =
        ImmutableListMultimap.<Integer, Integer>builder()
            .putAll(1, Lists.newArrayList(1, 2, 3, 4))
            .putAll(2, Lists.newArrayList(10, 2, 3, 4, 5))
            .orderValuesBy(Integer::compareTo)
            .build();
    System.out.println(listMultimap.inverse());
    System.out.println(listMultimap);
  }

  @Test
  public void testTreeMap() {
    TreeMultimap<Integer, Integer> treeMultimap = TreeMultimap.create();
    treeMultimap.putAll(1, Lists.newArrayList(4, 3, 1, 5));
    treeMultimap.putAll(0, Lists.newArrayList(2, 33, 41, 2));
    System.out.println(treeMultimap);
  }
}
