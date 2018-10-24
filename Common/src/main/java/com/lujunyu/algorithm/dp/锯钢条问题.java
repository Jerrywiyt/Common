package com.lujunyu.algorithm.dp;

/**
 * 有一个根钢条，长度为n，不同的长度的不同价格，使用p[]表示。
 * 现求这根钢条最多能卖多少钱Rn。
 * Rn=max(p[i-1]+R(n-i))  1<=i<=n;
 *
 * https://blog.csdn.net/u013309870/article/details/75193592
 */
public class 锯钢条问题 {
    public static void main(String args[]){
        int n = 4;
        int[] p = new int[]{1,5,8,9};

        System.out.println(test1(p,n));
        System.out.println(test2(p,n));
        System.out.println(test3(p));
    }
    //递归实现
    static int test1(int[] p,int n){
        if(n==0){
            return 0;
        }
        int r = 0;
        for(int i=1;i<=n;i++){
            r = Math.max(r,p[i-1]+test1(p,n-i));
        }
        return r;
    }

    //备忘录版本，原理是缓存中间结果，避免递归重复计算。
    static int test2(int[] p,int n){
        int[] c = new int[p.length];
        return test22(p,c,n);
    }

    private static int test22(int[] p, int[] c, int n) {
        if(n==0){
            return 0;
        }
        if(c[n-1]>0){
            return c[n-1];
        }
        int r = 0;
        for(int i=1;i<=n;i++){
            r = Math.max(r,p[i-1]+test1(p,n-i));
        }
        c[n-1] = r;
        return r;
    }
    //自低向上方法。

    public static int test3(int[] p){
        int[] c = new int[p.length+1];
        //外层循环是求r1到rn
        for(int i=1;i<=p.length;i++){
            //内层循环求ri
            int q = 0;
            for(int j=1;j<=i;j++){
                q = Math.max(q,p[j-1]+c[i-j]);
            }
            c[i] = q;
        }
        return c[p.length];
    }

}
