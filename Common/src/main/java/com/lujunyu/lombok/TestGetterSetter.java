package com.lujunyu.lombok;

import lombok.NonNull;
import lombok.Setter;

public class TestGetterSetter {

  /** @NonNUll 可以和@Setter @Data等配合使用 */
  @Setter
  public static class A {
    @NonNull String name;
  }

  public static void main(String[] args) {
    A a = new A();
    a.setName(null);
  }
}
