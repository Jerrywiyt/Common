package com.lujunyu.juice.binding;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.ToString;

@ToString
public class InstanceBindings {
  @Inject
  @Named("login timeout seconds")
  private int timesout;

  @Inject
  @Named("JDBC URL")
  private String url;
}
