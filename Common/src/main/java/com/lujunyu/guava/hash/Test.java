package com.lujunyu.guava.hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * 根据自己的需求测试各种hash算法的性能。
 *
 * @author lujunyu
 */
public class Test {
  public static void main(String[] args) throws IOException {
    //		testMurMurHash();
    //		testHashCode();
    //		testMd5();
    testGoodHash();
  }

  private static void testGoodHash() throws IOException {
    testHashFunction(
        new HashFunction() {

          @Override
          public int hash(String s) {
            return Hashing.goodFastHash(1).newHasher().putString(s, Charsets.UTF_8).hash().asInt();
          }
        });
  }

  private static void testMd5() throws IOException {
    testHashFunction(
        new HashFunction() {

          @Override
          public int hash(String s) {
            return Hashing.md5().newHasher().putString(s, Charsets.UTF_8).hash().asInt();
          }
        });
  }

  private static void testHashCode() throws IOException {
    testHashFunction(
        new HashFunction() {

          @Override
          public int hash(String s) {
            return s.hashCode();
          }
        });
  }

  private static Set<String> getImeis() throws IOException {
    File file =
        new File(
            "E:\\code\\myCode\\Common\\Common\\src\\main\\java\\com\\lujunyu\\guava\\hash\\imei");
    BufferedReader br = new BufferedReader(new FileReader(file));
    Set<String> set = new HashSet<String>(1000000);
    String line = "";
    while ((line = br.readLine()) != null) {
      set.add(line);
    }
    br.close();
    return set;
  }

  private static void testMurMurHash() throws IOException {
    testHashFunction(
        new HashFunction() {
          @Override
          public int hash(String s) {
            return Hashing.murmur3_32().newHasher().putString(s, Charsets.UTF_8).hash().asInt();
          }
        });
  }

  /**
   * 各类hash算法的性能测试。
   *
   * @param fun
   * @throws IOException
   */
  private static void testHashFunction(HashFunction fun) throws IOException {
    Set<String> set = getImeis();
    int[] res = new int[set.size()];
    int i = 0;
    long t1 = System.currentTimeMillis();
    for (String s : set) {
      res[i++] = fun.hash(s);
    }
    long t2 = System.currentTimeMillis();
    long time = t2 - t1;
    System.out.println(set.size() + "条数据耗时：" + time);
    testDisperse(res);
  }
  /** @param input */
  private static void testDisperse(int[] input) {
    Map<Integer, Integer> map1 = new TreeMap<Integer, Integer>();
    Map<Integer, Integer> map2 = new TreeMap<Integer, Integer>();
    Map<Integer, Integer> map3 = new TreeMap<Integer, Integer>();
    for (int i : input) {
      Integer num = map1.get(i);
      if (num == null) {
        map1.put(i, 1);
      } else {
        map1.put(i, ++num);
      }
      // 模16.
      int j = i & 0x0f;
      Integer num2 = map2.get(j);
      if (num2 == null) {
        map2.put(j, 1);
      } else {
        map2.put(j, ++num2);
      }

      int k = Math.abs(i % 100);
      Integer num3 = map3.get(k);
      if (num3 == null) {
        map3.put(k, 1);
      } else {
        map3.put(k, ++num3);
      }
    }
    System.out.println("方差：" + variance(map2, input.length, 16));
    System.out.println("方差：" + variance(map3, input.length, 100));
  }

  private static int variance(Map<Integer, Integer> map, int size, int n) {
    double mean = size / n;
    double res = 0;
    Set<Map.Entry<Integer, Integer>> entrys = map.entrySet();
    for (Map.Entry<Integer, Integer> entry : entrys) {
      res += (entry.getValue() - mean) * (entry.getValue() - mean);
    }
    int a = (int) (res / n);
    return a;
  }

  private static interface HashFunction {
    int hash(String s);
  }
}
