package com.lujunyu.juice.binding;

import com.lujunyu.juice.start.TransactionLog;
import com.lujunyu.juice.start.TransactionLogImpl;

import javax.inject.Provider;

public class ProviderBindings implements Provider<TransactionLog> {
  @Override
  public TransactionLog get() {
    return new TransactionLogImpl();
  }
}
