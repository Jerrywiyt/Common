package com.lujunyu.algorithm.dp;

/**
 * 一次只能爬一个或者两个台阶，问n楼有多少种方案。
 * 这道题属于dp里面教科书般的题，属于接触dp后，最开始碰到的题。
 *
 * 问题分析：
 * 假设n个台阶，有f(n)种走法。
 * 如果最后走一个台阶，那么前面的台阶一共有f(n-1)种走法。
 * 如果最后走两个台阶，那么前面的台阶一共有f(n-2)种走法。
 *
 * 最后f(n)=f(n-1)+f(n-2)，状态转移方程也就出来了。
 *
 */
public class 爬楼梯 {

    public static void main(String args[]){
        System.out.println(find1(10));
        System.out.println(find2(10));
    }

    /**
     * 递归。
     */
    private static int find1(int n){
        if(n<=0){
            throw new IllegalArgumentException("n must > 0");
        }
        if(n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        return find1(n-1)+find1(n-2);
    }

    /**
     * 自低向上查找。
     */
    private static int find2(int n){
        if(n<=0){
            throw new IllegalArgumentException("n must > 0");
        }
        if(n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        int a = 1;
        int b = 2;
        int i = 3;
        while(i<=n){
            a = b;
            b = a + b;
            i++;
        }
        return b;
    }

}
