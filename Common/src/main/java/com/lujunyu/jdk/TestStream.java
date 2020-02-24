package com.lujunyu.jdk;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.AbstractCollection;
import java.util.HashSet;
import org.junit.Test;

/** 研究一下Stream API的各种用法。 */
public class TestStream {

  /**
   * 求解集合的最大元素、最小元素等操作，可以使用reduce来进行处理。
   *
   * <p>Reduce表示减少，也就是说，它能把多个输入转化为一个输出。
   */
  @Test
  public void testReduce() {
    // 例子1：找出集合中最长的字符串。
    System.out.println(
        Lists.newArrayList("1", "11", "111").stream()
            .reduce((w1, w2) -> w2.length() > w1.length() ? w2 : w1)
            .orElse(""));

    // 例子2：求解元素的和。其中Identity参数用于表示初始值。
    System.out.println(
        Lists.newArrayList("1", "2", "3").stream().map(Integer::parseInt).reduce(10, Integer::sum));

    // 例子3：把元素放入同一个集合里面。Identity除了用于表示初始值外，也可以用于定义返回结果类型。
    HashSet<String> res = Sets.newHashSet();
    res.add("Identity");
    System.out.println(
        Lists.newArrayList("1", "2", "3").stream()
            .map(Sets::newHashSet)
            .reduce(
                res,
                (a, b) -> {
                  a.addAll(b);
                  return a;
                }));

    // 例子4：三个参数的玩法
    // watefall#reduce(U identity, U> U BiFunction<U, ? super T, U> accumulator, BinaryOperator<U>
    // combiner);
    // 第三个参数表示多个子任务的结果如何聚合，在并行计算的时候才会使用到，所以如果在单线程中，这个方法不会被调用到。
    System.out.println(
        Lists.newArrayList(1, 2, 3, 4, 5, 7, 8).stream()
            .parallel()
            .reduce(0, Integer::sum, (a, b) -> a - b));

    // 例子5：reduce中的元素与返回值不同时，第一种写法；由于泛型的限制，只能使用三个参数的方法，实际上第三个方法没有执行到。
    System.out.println(
        Lists.newArrayList(1, 2, 3, 4).stream()
            .reduce(
                Sets.newHashSet(),
                (a, b) -> {
                  a.add(b);
                  return a;
                },
                (a, b) -> {
                  a.addAll(b);
                  return a;
                }));
    // 第二种写法。
    System.out.println(
        Lists.newArrayList(1, 2, 3, 4).stream()
            .map(Lists::newArrayList)
            .reduce(
                Lists.newArrayList(),
                (a, b) -> {
                  a.addAll(b);
                  return a;
                }));
  }
}
