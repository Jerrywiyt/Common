package com.lujunyu.algorithm.sort;

import com.alibaba.fastjson.JSON;

public class QuickSort {

  public static void main(String args[]) {
    int[] arr = new int[] {3, 2, 1, 4, 5, 8, 4, 5};

    quickSort(arr, 0, arr.length - 1);
    System.out.println(JSON.toJSONString(arr));
  }

  /**
   * 思路： 1. 选取一个基准。 2. 把比基准大的数放到右边。 3. 把比基准小的数放到左边。
   *
   * <p>为了避免大量的元素交换，采用填坑法来做。
   */
  private static void quickSort(int[] arr, int left, int right) {
    if (arr == null || arr.length == 0) {
      return;
    }
    if (left < right) {
      int mid = arr[left];
      int low = left;
      int high = right;
      while (low < high) {
        while (low < high && arr[high] >= mid) {
          high--;
        }
        arr[low] = arr[high];
        while (low < high && arr[low] <= mid) {
          low++;
        }
        arr[high] = arr[low];
      }
      arr[low] = mid;
      quickSort(arr, left, low - 1);
      quickSort(arr, low + 1, right);
    }
  }
}
