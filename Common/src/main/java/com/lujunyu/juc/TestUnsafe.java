package com.lujunyu.juc;

import org.junit.Test;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class TestUnsafe {

    /**
     * unsafe只能通过反射获取。
     */
    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        System.out.println(unsafe);
    }

    /**
     *  内存方面的操作。
     * @throws Exception
     */
    @Test
    public void testMemory() throws Exception {
        Unsafe unsafe = getUnsafe();
        long add = unsafe.allocateMemory(1000);
        unsafe.reallocateMemory(add,1000);
        unsafe.setMemory(add,2000, (byte) 0);
        unsafe.getLong(add);
        unsafe.freeMemory(add);
        //操作对象的内存。
        long offset = unsafe.objectFieldOffset(People.class.getDeclaredField("i"));
        People p = new People();
        unsafe.putInt(p,offset,100);
        System.out.println(p.i);
    }

    @Test
    public void testCas() throws Exception {
        Unsafe unsafe = getUnsafe();
        People p = new People();
        long offset = unsafe.objectFieldOffset(People.class.getDeclaredField("i"));
        System.out.println(offset);
        System.out.println( unsafe.compareAndSwapInt(p,offset,1,1));
        System.out.println( unsafe.compareAndSwapInt(p,offset,0,1));
    }

    /**
     * 线程方面的操作。
     * @throws Exception
     */
    @Test
    public void testThread() throws Exception {
        Unsafe unsafe = getUnsafe();
        //这个单位是纳秒。
        unsafe.park(false,TimeUnit.SECONDS.toNanos(1));
        unsafe.unpark(Thread.currentThread());
        System.out.println("absolute");
        //看看第一个参数的效果。
        unsafe.park(true,10);
    }


    /**
     * 对象操作。
     * @throws Exception
     */
    @Test
    public void testObj() throws Exception {
        Unsafe unsafe = getUnsafe();

        long offset = unsafe.objectFieldOffset(People.class.getDeclaredField("i"));
        People p = new People();

        System.out.println(unsafe.getLong(p,offset));
        System.out.println(unsafe.getLongVolatile(p,offset));
        unsafe.putLongVolatile(p,offset,111);
        System.out.println(unsafe.getLongVolatile(p,offset));
    }


    private static class People {

        private int i = 1;

    }
    private Unsafe getUnsafe() throws Exception {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (Unsafe) field.get(null);
    }
}
