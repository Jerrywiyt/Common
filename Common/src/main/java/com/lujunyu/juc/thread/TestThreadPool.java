package com.lujunyu.juc.thread;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class TestThreadPool {
    /**
     * 此方法证明， t.interrupt方法能够唤醒处于park状态的线程，但不抛出异常，作用相当于调用了unpark方法。
     */
    @Test
    public void test(){
        Thread t = new Thread(() -> {
            LockSupport.park();
            System.out.println("end");
            //此方法可以清楚中断标识。
            System.out.println(Thread.interrupted());
            LockSupport.park();
            System.out.println("end");
        });
        t.start();
        t.interrupt();

        LockSupport.park();
    }

    @Test
    public void testFutureTask(){
        Executors.newFixedThreadPool(1).submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
