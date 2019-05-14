package com.lujunyu.juc.thread;

import org.junit.Test;

public class TestThreadLocal {
    @Test
    public void test(){
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("value");
        threadLocal.get();
        threadLocal.remove();
    }
}
