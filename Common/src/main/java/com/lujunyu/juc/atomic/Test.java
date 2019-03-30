package com.lujunyu.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    @org.junit.Test
    public void testAtomicInteger(){
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.getAndIncrement();
    }
}
