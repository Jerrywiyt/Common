package com.lujunyu.design.visitor;

public class NodeA implements Node {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitor(this);
  }

  public String toString() {
    return "nodeA";
  }
}
