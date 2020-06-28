package com.lujunyu.juice.binding;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor(onConstructor = @__(@Inject))
public class TestNameAnnotation {
  @Inject
  @Named("TestNameAnnotation")
  private Apple apple;

  /*  @Inject
  public TestNameAnnotation(@Named("TestNameAnnotation") Apple apple) {
    this.apple = apple;
  }*/
}
