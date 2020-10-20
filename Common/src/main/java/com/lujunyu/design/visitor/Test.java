package com.lujunyu.design.visitor;

public class Test {

  public static void main(String[] args) {
    Client c = new Client();
    c.add(new NodeA());
    c.add(new NodeB());

    c.visitor(new Visitor1());
    c.visitor(new Visitor2());
  }
}
