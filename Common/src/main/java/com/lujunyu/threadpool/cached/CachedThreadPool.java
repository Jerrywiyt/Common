package com.lujunyu.threadpool.cached;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lujunyu.threadpool.AbortPolicyWithReport;
import com.lujunyu.threadpool.NamedThreadFactory;
import com.lujunyu.threadpool.ThreadPool;

public class CachedThreadPool implements ThreadPool {

  private String name;

  private int cores;

  private int maxThread;

  private int alive;

  private RejectedExecutionHandler rejectedHandler = new AbortPolicyWithReport(name);

  private ThreadFactory threadFactory = new NamedThreadFactory(name, true);

  private Executor executor;

  public CachedThreadPool() {}

  public void initialize() {
    executor =
        new ThreadPoolExecutor(
            cores,
            maxThread,
            alive,
            TimeUnit.MILLISECONDS,
            new SynchronousQueue<Runnable>(),
            threadFactory,
            rejectedHandler);
  }

  @Override
  public Executor getExecutor() {
    return executor;
  }

  @Override
  public void execute(Runnable command) {
    executor.execute(command);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCores() {
    return cores;
  }

  public void setCores(int cores) {
    this.cores = cores;
  }

  public int getThreads() {
    return maxThread;
  }

  public void setThreads(int threads) {
    this.maxThread = threads;
  }

  public int getAlive() {
    return alive;
  }

  public void setAlive(int alive) {
    this.alive = alive;
  }

  public RejectedExecutionHandler getRejectedHandler() {
    return rejectedHandler;
  }

  public void setRejectedHandler(RejectedExecutionHandler rejectedHandler) {
    this.rejectedHandler = rejectedHandler;
  }

  public ThreadFactory getThreadFactory() {
    return threadFactory;
  }

  public void setThreadFactory(ThreadFactory threadFactory) {
    this.threadFactory = threadFactory;
  }

  public void setExecutor(Executor executor) {
    this.executor = executor;
  }
}
