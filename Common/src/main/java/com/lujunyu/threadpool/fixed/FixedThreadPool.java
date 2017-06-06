
package com.lujunyu.threadpool.fixed;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lujunyu.threadpool.AbortPolicyWithReport;
import com.lujunyu.threadpool.NamedThreadFactory;
import com.lujunyu.threadpool.ThreadPool;

/**
 * 此线程池启动时即创建固定大小的线程数，不做任何伸缩，来源于：<code>Executors.newFixedThreadPool()</code>
 * 
 * @see java.util.concurrent.Executors#newFixedThreadPool(int)
 */
public class FixedThreadPool implements ThreadPool {

	private String name;

	private int threads;

	private int queues;

	private RejectedExecutionHandler rejectedHandler = new AbortPolicyWithReport(name);

	private ThreadFactory threadFactory = new NamedThreadFactory(name, false);

	private Executor executor;
	
	public FixedThreadPool(int threads){
		this(threads, Integer.MAX_VALUE,"pool");
	}
	public FixedThreadPool(int threads,int queues,String name){
		this.threads = threads;
		this.queues = queues;
		this.name = name;
		initialize();
	}
	public void initialize() {
		executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS, queues == 0
				? new SynchronousQueue<Runnable>()
				: (queues < 0 ? new LinkedBlockingQueue<Runnable>() : new LinkedBlockingQueue<Runnable>(queues)),
				threadFactory, rejectedHandler);
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

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getQueues() {
		return queues;
	}

	public void setQueues(int queues) {
		this.queues = queues;
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