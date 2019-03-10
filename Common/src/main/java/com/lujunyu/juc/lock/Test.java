package com.lujunyu.juc.lock;

import java.util.concurrent.locks.LockSupport;

public class Test {
    public static void main(String[] args){
        LockSupport.park();


        Thread.interrupted();
    }
}
