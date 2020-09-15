package com.lujunyu.jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class TestCase {

  @Test
  public void transferBetweenIntArrayAndList() {
    List<Integer> list = new ArrayList<>();
    list.add(1);

    int[] ints = list.stream().mapToInt(Integer::intValue).toArray();

    List<Integer> collect = Arrays.stream(ints).boxed().collect(Collectors.toList());
  }
}
