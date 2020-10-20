package com.lujunyu.design.visitor;

import java.util.ArrayList;
import java.util.List;

public class Client {
  private List<Node> nodeList = new ArrayList<Node>();

  /**
   * 初始化的时候，添加节点。
   *
   * @param node
   */
  public void add(Node node) {
    nodeList.add(node);
  }

  /**
   * 对外提供访问者接口。
   *
   * @param visitor
   */
  public void visitor(Visitor visitor) {
    for (Node node : nodeList) {
      node.accept(visitor);
    }
  }
}
