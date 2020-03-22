package com.lujunyu.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;


public class CListener {

  /**
   * 可以用于统计死信事件
   */
  @Subscribe
  void handleMessage(DeadEvent deadEvent){
    System.out.println(deadEvent.getEvent().getClass().getSimpleName());
  }

  /**
   * 可以用于统计所有事件记录。
   */
  @Subscribe
  void recordAllEvent(Object obj){
    System.out.println(obj.getClass().getSimpleName());
  }
}
