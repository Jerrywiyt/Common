package com.lujunyu.algorithm.struct.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Heap {

  public static void main(String[] args) {
    int[] arr = new int[]{1,2,3,4,5};
    buildHeap(arr);
    heapSort(arr);
    PriorityQueue priorityQueue = new PriorityQueue();
    priorityQueue.add(1);
    priorityQueue.add(2);
    priorityQueue.add(3);
    priorityQueue.add(4);
    priorityQueue.add(5);
    priorityQueue.add(6);
    priorityQueue.add(7);
    System.out.println(priorityQueue.poll());
    System.out.println(priorityQueue.poll());
    System.out.println(priorityQueue.poll());
    priorityQueue.add(8);
    System.out.println(priorityQueue.poll());
    priorityQueue.add(9);
    System.out.println(priorityQueue.poll());
    priorityQueue.add(0);
    System.out.println(priorityQueue.poll());
    System.out.println(priorityQueue.poll());
    System.out.println(priorityQueue.poll());
    System.out.println(priorityQueue.poll());
    System.out.println(priorityQueue.poll());

    System.out.println(findKBiggestNum(arr,2));
  }

  private static int findKBiggestNum(int[] arr,int k){
    buildHeap(arr);
    for(int i = arr.length-1;i>arr.length-k;i--){
      int temp = arr[i];
      arr[i] = arr[0];
      arr[0] = temp;
      adjust(arr,0,i);
    }
    return arr[0];
  }

  /**
   * 大堆 -> 升序
   * 小堆 -> 降序
   */
  private static void heapSort(int[] arr){
    for(int i=arr.length/2;i>=0;i--){
      adjust(arr,i,arr.length);
    }

    //其中的i表示堆的长度，将最大值放到最后，这样不会破坏堆的结构。
    for(int i = arr.length-1;i>0;i--){
      int temp = arr[0];
      arr[0] = arr[i];
      arr[i] = temp;

      adjust(arr,0,i);
    }
  }

  /**
   * 初始化一个大顶堆。
   */
  private static void buildHeap(int[] arr){
    for(int i=arr.length/2;i>=0;i--){
      adjust(arr,i,arr.length);
    }
  }

  /**
   * 向下调整。
   *
   * 左：2*n+1
   * 右：2*n+2
   */
  private static void adjust(int[] arr,int index,int len){
    if(index<len/2){
      int l = index*2+1;
      int r = index*2+2;
      int max = index;
      if(l<len&&arr[l]>arr[max]){
        max = l;
      }
      if(r<len&&arr[r]>arr[max]){
        max = r;
      }
      if(max!=index){
        int temp = arr[max];
        arr[max] = arr[index];
        arr[index] = temp;
        adjust(arr,max,len);
      }
    }
  }

  /**
   * 采用堆构建一个最大优先队列。
   * 支持，add, poll等操作。
   */
  private static class PriorityQueue {
    List<Integer> list = new ArrayList<>();

    public void add(int val){
      list.add(val);
      siftUp(list.size()-1);
    }

    private void siftUp(int index){
      if(index>0){
        int parent = (index-1)>>2;
        if(list.get(index)>list.get(parent)){
          Collections.swap(list,parent,index);
          siftUp(parent);
        }
      }
    }

    /**
     * 注意删除元素的时候，要把最后一个元素替换掉头元素，
     * 而不是直接将头元素移除，这样做的目的是不需要过多的调整heap.
     */
    public int poll(){
      if(list.isEmpty()){
        throw new RuntimeException("queue is empty!!");
      }
      int val = list.get(0);
      list.set(0,list.get(list.size()-1));
      list.remove(list.size()-1);
      siftDown(0);
      return val;
    }

    private void siftDown(int index){
      if(index<list.size()/2){
        int l = index*2 + 1;
        int r = index*2 + 2;
        int max = index;
        if(l<list.size()&&list.get(l)>list.get(max)){
          max = l;
        }
        if(r<list.size()&&list.get(r)>list.get(max)){
          max = r;
        }
        if(max!=index){
          Collections.swap(list,max,index);
          siftDown(max);
        }
      }
    }
  }
}
