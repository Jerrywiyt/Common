package com.lujunyu.proxy;

import com.lujunyu.proxy.jdk.ProxyOperate;
import org.junit.Test;

public class TestProxy {
  @Test
  public void testJdkProxy() {
    IOperate operate = new ProxyOperate().getInstance(new OperateImpl());
    operate.print("jdk proxy");
  }
}
