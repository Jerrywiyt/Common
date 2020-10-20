package com.lujunyu.design.visitor;

public class Visitor1 implements Visitor {

  @Override
  public void visitor(Node node) {
    // 处理不同的node，
    // 这里
    System.out.println("visitor1 deal " + node);
  }
}
