package com.lujunyu.algorithm.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 求参加最多的会议数目，贪婪思想。
 *
 * <p>一般这种扫描类的算法，采用的都是贪婪算法。
 *
 * <p>分别遍历每一天，然后找出适合参加的会议，然后在所有合适的会议中挑选结束时间最早的会议。
 */
public class Question1353 {
  public static void main(String[] args) {
    int[][] events = new int[][] {{1, 4}, {4, 4}, {2, 2}, {3, 4}, {1, 1}};
    System.out.println(test(events));
    System.out.println(test1(events));
  }

  public static int test1(int[][] events) {
    if (events == null || events.length == 0) {
      return 0;
    }
    Arrays.sort(events, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
    PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((Comparator.comparingInt(o -> o[1])));

    int idx = 0;
    int res = 0;
    int day = 1;

    while (idx < events.length || priorityQueue.size() > 0) {
      while (idx < events.length && events[idx][0] <= day) {
        priorityQueue.add(events[idx]);
        idx++;
      }
      while (priorityQueue.size() > 0) {
        int[] temp = priorityQueue.poll();
        if (temp[0] <= day && day <= temp[1]) {
          res++;
          break;
        }
      }
      day++;
    }
    return res;
  }

  /** 采用最大堆来实现。 */
  public static int test(int[][] events) {
    if (events == null) {
      return 0;
    }
    PriorityQueue<int[]> priorityQueue =
        new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
    priorityQueue.addAll(Arrays.asList(events));
    int num = 0;
    int curDay = 1;
    int lastRecord = 0;
    while (priorityQueue.size() > 0) {
      int[] event = priorityQueue.poll();
      // 直接使用
      if (event[1] == curDay || (curDay == event[0] && lastRecord == event[1])) {
        num++;
        curDay++;
        // 将来参加。
      } else if (curDay <= event[0]) {
        curDay = event[0];
        priorityQueue.add(new int[] {curDay, event[1]});
        lastRecord = event[1];
      } else if (curDay < event[1]) {
        priorityQueue.add(new int[] {curDay, event[1]});
        lastRecord = event[1];
      }
    }
    return num;
  }
}
