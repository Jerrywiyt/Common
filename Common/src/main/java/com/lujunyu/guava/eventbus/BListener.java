package com.lujunyu.guava.eventbus;

import com.google.common.eventbus.Subscribe;

public class BListener {
  @Subscribe
  public void comsume(EventB eventB) {
    System.out.println(
        this.getClass().getSimpleName() + " receive " + eventB.getClass().getSimpleName());
  }
}
