package com.lujunyu.commons.pool;

import java.util.concurrent.atomic.AtomicInteger;

public class Connection {
  private static AtomicInteger atomic = new AtomicInteger();

  public static Connection create() {
    Connection c = new Connection();
    c.id = atomic.incrementAndGet();
    return c;
  }

  public static void destroy(Connection c) {
    c.close();
  }

  public static boolean isValid(Connection c) {
    return c.isAlive;
  }

  private int id;
  private boolean isAlive = true;

  public void close() {
    System.out.println(this.id + " is close");
  }

  public int getId() {
    return this.id;
  }
}
