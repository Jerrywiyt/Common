package com.lujunyu.guava.eventbus;

import com.google.common.eventbus.Subscribe;

public class AListener {
  @Subscribe
  public void consume(EventA eventA) {
    System.out.println(
        this.getClass().getSimpleName() + " receive " + eventA.getClass().getSimpleName());

    throw new RuntimeException("");
  }
}
