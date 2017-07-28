package com.lujunyu.juc;



//30034924065
//19988221406

//25053809409
//20003929043

//23647690018
//22083998484
/**
 * 探讨伪共享问题，
 * 上面是没有填充cache line的结果，下面是填充cache line的结果。实验证明缓存行填充确实有效果，但并不是特别明显。
 */
public class FalseSharing implements Runnable{

	public final static int NUM_THREADS = 1;
	public final static long ITERATIONS = 500L*1000L*1000L;
	public final int arrayIndex;
	private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
	static{
		for(int i=0;i<longs.length;i++){
			longs[i] = new VolatileLong();
		}
	}
	public FalseSharing(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}
	public static void main(String[] args) throws InterruptedException {
		final long start  = System.nanoTime();
		runTest();
		System.out.println("duration="+(System.nanoTime()-start));
	}
	private static void runTest() throws InterruptedException {
		Thread[] threads = new Thread[NUM_THREADS];
		for(int i=0;i<threads.length;i++){
			threads[i] = new Thread(new FalseSharing(i));
		}
		for(Thread t : threads){
			t.start();
		}
		for(Thread t : threads){
			t.join();
		}
	}
	@Override
	public void run() {
		long i = ITERATIONS -1;
		while(0!=--i){
			longs[arrayIndex].value = i;
		}
	}
	
	public final static class VolatileLong{
		public volatile long value = 0L;
		public long p1,p2,p3,p4,p5,p6;
	}

}
