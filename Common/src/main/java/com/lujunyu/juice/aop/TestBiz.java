package com.lujunyu.juice.aop;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.matcher.Matchers;

/** 使用guice的aop。使用起来比较简单，下面就是一个简单的demo。 */
public class TestBiz {

  @Log
  public void test(String args) {
    System.out.println("deal: " + args);
  }

  public static void main(String args[]) {
    Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                bindInterceptor(
                    Matchers.any(), Matchers.annotatedWith(Log.class), new LogInterceptor());
              }
            })
        .getInstance(TestBiz.class)
        .test("123");
  }
}
