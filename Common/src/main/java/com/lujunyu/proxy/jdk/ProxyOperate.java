package com.lujunyu.proxy.jdk;

import com.lujunyu.proxy.IOperate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyOperate implements InvocationHandler {
  private IOperate real;

  public IOperate getInstance(IOperate operate) {
    this.real = operate;
    return (IOperate)
        Proxy.newProxyInstance(
            ProxyOperate.class.getClassLoader(), new Class[] {IOperate.class}, this);
  }

  /** proxy 代理类 method 执行的方法 args 方法参数。 */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("before execute");
    try {
      method.invoke(real, args);
    } catch (Exception e) {
      System.out.println("exception execute");
    }
    System.out.println("end execute");
    return null;
  }
}
