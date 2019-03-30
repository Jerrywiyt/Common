package com.lujunyu.juc.queue;

import java.util.concurrent.SynchronousQueue;

public class TestSynchronousQueue {

    private static SynchronousQueue<String> queue = new SynchronousQueue<>();


    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override
            public void run() {

                while(true){
                    try {
                        System.out.println(queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        for (int i=0;i<10;i++){
            queue.put(""+i);
        }
    }
}
