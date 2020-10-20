package com.lujunyu.jvm.classloader;

import org.junit.Test;

public class TestClassLoader {

  /** 亲测，如果先通过Ctrl+F9进行编译后，然后找到A的class文件，然后删除运行下列代码会抛出NoClassDefFoundError。 */
  @Test
  public void testNoClassDefFoundError() {
    new A();
  }
}
