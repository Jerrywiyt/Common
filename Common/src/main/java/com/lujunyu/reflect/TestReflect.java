package com.lujunyu.reflect;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class TestReflect {
  @Test
  public void testApi() {
    Class<TestInterface> clazz = TestInterface.class;
    Method[] methods = clazz.getDeclaredMethods();
    for (Method method : methods) {
      Class[] clazz1 = method.getParameterTypes();
      Type[] types = method.getGenericParameterTypes();
      for (Class c : clazz1) {
        System.out.println(c.getName());
      }
      for (Type type : types) {
        System.out.println(type);
      }
    }
  }
}
