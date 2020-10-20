package com.lujunyu.proxy.javassist;

import com.lujunyu.proxy.IOperate;
import javassist.util.proxy.ProxyFactory;
import org.junit.Test;

public class TestJavassist {
  @Test
  public void testJavassistFactory() {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setInterfaces(new Class[] {IOperate.class});
    Class proxyClass = proxyFactory.createClass();
  }
}
