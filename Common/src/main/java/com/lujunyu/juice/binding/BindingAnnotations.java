package com.lujunyu.juice.binding;

import com.google.inject.Inject;
import com.lujunyu.juice.start.TransactionLog;
import lombok.ToString;

import javax.inject.Named;

@ToString
public class BindingAnnotations {
  @Inject
  //    @TranImpl
  @Named("TranImpl")
  private TransactionLog transactionLog;
}
