package com.lujunyu.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author jerry
 */
public class HeadOOM {
  static class OOMObject {}

  public static void main(String[] args) {
    List<OOMObject> list = new ArrayList<OOMObject>();
    while (true) {
      list.add(new OOMObject());
    }
  }
}
