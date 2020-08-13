package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Question381 {
  public static void main(String[] args) {
    RandomizedCollection collection = new RandomizedCollection();
    collection.insert(0);
    collection.remove(0);
    collection.insert(-1);
    collection.remove(0);

    collection.getRandom();
  }

  static class RandomizedCollection {
    List<Integer> list = new ArrayList<>();
    Map<Integer, Set<Integer>> map = new HashMap<>();
    /** Initialize your data structure here. */
    public RandomizedCollection() {}

    /**
     * Inserts a value to the collection. Returns true if the collection did not already contain the
     * specified element.
     */
    public boolean insert(int val) {
      list.add(val);
      Set<Integer> indexs = map.get(val);
      if (indexs == null || indexs.size() == 0) {
        indexs = new HashSet<>();
        map.put(val, indexs);
        indexs.add(list.size() - 1);
        return true;
      } else {
        indexs.add(list.size() - 1);
        return false;
      }
    }

    /**
     * Removes a value from the collection. Returns true if the collection contained the specified
     * element.
     */
    public boolean remove(int val) {
      Set<Integer> indexs = map.get(val);
      if (indexs != null && indexs.size() > 0) {
        int index = indexs.iterator().next();
        indexs.remove(index);
        int last = list.get(list.size() - 1);
        list.set(index, last);
        list.remove(list.size() - 1);
        if (index != list.size()) {
          Set<Integer> indexsOfLast = map.get(last);
          indexsOfLast.remove(list.size());
          indexsOfLast.add(index);
        }
        return true;
      }
      return false;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
      if (list.size() > 0) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
      }
      throw new RuntimeException();
    }
  }

  /**
   * Your RandomizedCollection object will be instantiated and called as such: RandomizedCollection
   * obj = new RandomizedCollection(); boolean param_1 = obj.insert(val); boolean param_2 =
   * obj.remove(val); int param_3 = obj.getRandom();
   */
}
