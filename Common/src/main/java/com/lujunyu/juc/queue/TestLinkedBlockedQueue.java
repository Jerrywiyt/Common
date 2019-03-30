package com.lujunyu.juc.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class TestLinkedBlockedQueue {

    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);



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
