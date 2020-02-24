package com.lujunyu.guava.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

/**
 * 使用场景：当我们想要一个一对一映射的数据结构时，可以使用BiMap
 *
 * <p>相当于：同时维护Map<K,V>和Map<V,K>，所以在使用BiMap的时候，需要确保value的唯一。
 */
public class TestBiMap {

  @Test
  public void test() {
    BiMap<String, String> biMap = HashBiMap.create();
    biMap.put("1", "2");
    biMap.put("2", "3");
    System.out.println(biMap);
    System.out.println(biMap.get("2"));
    System.out.println(biMap.inverse().get("3"));
  }

  /** 使用此Map value不能重复 */
  @Test
  public void testException() {
    BiMap<String, String> biMap = HashBiMap.create();
    biMap.put("1", "1");
    biMap.put("2", "1");
  }

  @Test
  public void testEnumMap() {
    EnumHashBiMap<APP, String> hashBiMap = EnumHashBiMap.create(APP.class);
    hashBiMap.put(APP.A1, "1");
    hashBiMap.put(APP.A2, "2");
    System.out.println(hashBiMap);
    System.out.println(hashBiMap.get(APP.A1));
    System.out.println(hashBiMap.inverse().get("1"));
  }

  enum APP {
    A1,
    A2
  }
}
