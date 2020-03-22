package com.lujunyu.guava.eventbus;

import com.google.common.eventbus.EventBus;

public class EventBusManager {

  public static void main(String[] args) {
    EventBus eventBus = new EventBus();
    eventBus.register(new AListener());
    eventBus.register(new BListener());
    eventBus.register(new CListener());


    eventBus.post(new EventA());
    eventBus.post(new EventB());

    //实践证明，继承类也能被识别成功。
    eventBus.post(new EventC());
    eventBus.post("11");
  }
}
