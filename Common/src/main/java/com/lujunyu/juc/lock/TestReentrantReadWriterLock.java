package com.lujunyu.juc.lock;

import org.junit.Test;
import org.omg.CORBA.TIMEOUT;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReentrantReadWriterLock {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
    @Test
    public void test(){
        lock.readLock().lock();
        lock.readLock().unlock();

        lock.writeLock().lock();
        lock.writeLock().unlock();
    }


    /**
     * 验证：
     * 线程先获取了写锁，再去获取读锁。
     */
    @Test
    public void test1(){
        lock.writeLock().lock();
        lock.readLock().lock();
        System.out.println("success");
        new Thread(() -> {
            lock.writeLock().lock();
            System.out.println("success..");
        }).start();
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        System.out.println("end");
        lock.readLock().unlock();
        lock.writeLock().unlock();
    }

    /**
     * 探究AQS的共享模式。
     * 共、共、独、共
     */
    @Test
    public void test2(){
        new Thread(){
            @Override
            public void run() {
                lock.writeLock().lock();
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
                lock.writeLock().unlock();
            }
        }.start();

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        new Thread(){
            @Override
            public void run() {
                lock.readLock().lock();
                System.out.println("readLock1");
            }
        }.start();
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        new Thread(){
            @Override
            public void run() {
                lock.readLock().lock();
                System.out.println("readLock2");
            }
        }.start();

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        new Thread(){
            @Override
            public void run() {
                lock.writeLock().lock();
                System.out.println("writeLock");
            }
        }.start();

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        new Thread(){
            @Override
            public void run() {
                lock.readLock().lock();
                System.out.println("readLock3");
            }
        }.start();
        System.out.println("start");
        LockSupport.parkNanos(TimeUnit.HOURS.toNanos(1));
    }


    /**
     * 测试，如果一个线程获取了读锁，然后另一个线程获取了写锁。
     * 下一个线程来的时候能不能获取读锁。
     */
    @Test
    public void testReadLock(){
        new Thread(){
            @Override
            public void run() {
                lock.readLock().lock();
                System.out.println("线程一获取了读锁。");
            }
        }.start();
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        new Thread(){
            @Override
            public void run() {
                System.out.println("线程二等待获取写锁。");
                lock.writeLock().lock();
            }
        }.start();

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        new Thread(){
            @Override
            public void run() {
                System.out.println("线程三尝试获取读锁");
                lock.readLock().lock();
                System.out.println("线程三获取读锁成功");
            }
        }.start();

        LockSupport.parkNanos(TimeUnit.HOURS.toNanos(1));
    }

    /**
     * 验证一个线程获取了读锁后它能不能获取写锁。
     *
     * 答案是否定的。
     */
    @Test
    public void testWriteLock(){
/*        lock.writeLock().lock();
        lock.readLock().lock();
        System.out.println("获取读锁成功");*/

        lock.readLock().lock();
        lock.writeLock().lock();
        System.out.println("获取写锁成功");

    }
}
