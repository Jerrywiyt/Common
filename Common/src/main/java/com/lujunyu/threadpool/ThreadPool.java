package com.lujunyu.threadpool;

import java.util.concurrent.Executor;

/** */
public interface ThreadPool {

  public Executor getExecutor();

  public void execute(Runnable command);
}
