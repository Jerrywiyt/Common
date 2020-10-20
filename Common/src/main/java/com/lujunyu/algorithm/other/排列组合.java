package com.lujunyu.algorithm.other;

public class 排列组合 {
  public static void main(String[] args) {
    int[] arr = new int[] {1, 2, 3};
    print(arr);
  }

  private static void print(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }
    select(arr, 0);
  }

  private static void select(int[] arr, int offset) {
    if (offset == arr.length - 1) {
      for (int a : arr) {
        System.out.print(a + " ");
      }
      System.out.println("");
    }
    for (int i = offset; i < arr.length; i++) {
      swap(arr, offset, i);
      select(arr, offset + 1);
      swap(arr, offset, i);
    }
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
