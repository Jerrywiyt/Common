package com.lujunyu.juice.best;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import lombok.ToString;

@ToString
public class Tree1 {
  @Inject private Fruit fruit;
}
