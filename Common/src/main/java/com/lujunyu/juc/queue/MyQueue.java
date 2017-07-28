package com.lujunyu.juc.queue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、获取元素时，当队列大小为空时，则阻塞。
 * 2、存取元素是，当队列已经满时，则阻塞。
 * 
 * TODO
 * @author lujunyu
 *
 * @param <E>
 */
public class MyQueue<E> {
	private LinkedList<E> nodes = new LinkedList<E>();
	private int capacity = Integer.MAX_VALUE;
	private AtomicInteger count = new AtomicInteger(0);
	private ReentrantLock putLock = new ReentrantLock();
	private Condition notFull = putLock.newCondition();
	private ReentrantLock takeLock = new ReentrantLock();
	private Condition notEmpty = takeLock.newCondition();
	public MyQueue(int capacity) {
		this.capacity = capacity;
	}
	
	public E take() throws InterruptedException{
		takeLock.lock();
		E e;
		int c = -1;
		try{
			while(count.get()==0){
				notEmpty.await();
			}
			e = nodes.removeFirst();
			c = count.decrementAndGet();
			if(c-1>0){
				notEmpty.signal();
			}
		}finally{
			takeLock.unlock();
		}
		if(c==capacity){
			
		}
		return e;
	}
	public void put(E e) throws InterruptedException{
		if(e==null){
			throw new NullPointerException();
		}
		putLock.lock();
		try{
			while(capacity==count.get()){
				notFull.await();
			}
			nodes.addLast(e);
			int c = count.getAndIncrement();
			if(c+1<capacity){
				notFull.signalAll();
			}
		}finally{
			putLock.unlock();
		}
		
	}
}
