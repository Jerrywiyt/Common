package com.lujunyu.algorithm.other;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static java.util.Collections.swap;

/** 洗牌算法 */
public class Shuffle {

  private List<Integer> sample = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

  @Test
  public void test() {
    System.out.println(JSON.toJSONString(shuffle1(Lists.newArrayList(sample))));
  }

  private List<Integer> shuffle1(List<Integer> list) {
    Random r = new Random();
    if (CollectionUtils.isEmpty(list)) {
      return list;
    }
    for (int i = list.size(); i > 0; i--) {
      swap(list, i - 1, r.nextInt(i));
    }
    return list;
  }
}
