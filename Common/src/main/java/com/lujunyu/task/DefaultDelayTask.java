package com.lujunyu.task;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;

/**
 * 采用时间轮的方式实现延时触发任务
 * 
 * @author jerry
 *
 */
public class DefaultDelayTask implements DelayTask {
	private static Logger log = Logger.getLogger(DefaultDelayTask.class);

	private volatile int curIndex;
	private Set<Task>[] timeWheel;
	private int size;
	// 时间单位秒。
	private int interval;
	private Executor threadpool;
	
	//临时存放的新加入任务。
	private Set<Task>[] temp;
	/**
	 * @param size
	 *            时间轮的大小
	 * @param interval
	 *            扫描时间轮的间隔时间。
	 * @param threadpool
	 */
	public DefaultDelayTask(int size, int interval, Executor threadpool) {
		super();
		this.size = size;
		this.interval = interval;
		this.threadpool = threadpool;
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		this.timeWheel = new Set[size];
		this.temp = new Set[size];
		for (int i = 0; i < size; i++) {
			this.timeWheel[i] = new HashSet<Task>();
			this.temp[i] = new HashSet<Task>();
		}
		this.curIndex = 0;
		startDelayTask();
	}

	private void startDelayTask() {
		Thread t = new Thread(){
			public void run(){
				while (true) {
					try {
						int index = curIndex;
						//获取当前任务。
						Set<Task> curTasks = DefaultDelayTask.this.timeWheel[index];
						log.info("current task size:"+curTasks.size());
						if(curTasks.size()>0){
							for(Task task:curTasks){
								threadpool.execute(new Runnable() {
									@Override
									public void run() {
										try{
											task.execute();
										}catch(Exception e){
											log.error("execute task fail:",e);
										}
									}
								});
							}
						}
						//设置扫描间隔。
						Thread.sleep(interval * 1000);
						//时间轮中放入新加入任务。
						DefaultDelayTask.this.timeWheel[index] = DefaultDelayTask.this.temp[index];
						//重置索引。
						int lastIndex = index;
						index++;
						// write volatile
						curIndex = index>=size?0:index;
						//临时任务置空。
						temp[lastIndex] = new HashSet<Task>();
					} catch (Exception e) {
						//防止任务意外断掉。
						log.error(e);
					}
				}
			}
		};
		t.setName("DefaultDalayTask");
		t.start();
	}
	
	@Override
	public void addTask(Task t) {
		//read volatile，保证任务不会丢失。
		this.temp[curIndex].add(t);
	}
}
