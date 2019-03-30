package com.lujunyu.juc.lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    private ReentrantLock lock = new ReentrantLock();
    @Test
    public void testLock(){
        try{
            lock.lock();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    @Test
    public void testCondition(){
        Condition condition = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                System.out.println("begin await");
                condition.await();
                System.out.println("end await");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));

        System.out.println("signal");
        try{
            lock.lock();
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
    }
}
