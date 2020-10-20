package com.lujunyu.design.visitor;

public interface Node {
  void accept(Visitor visitor);
}
