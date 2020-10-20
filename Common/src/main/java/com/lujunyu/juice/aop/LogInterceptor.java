package com.lujunyu.juice.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogInterceptor implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    System.out.println("before");
    Object res = invocation.proceed();
    System.out.println("end");
    return res;
  }
}
