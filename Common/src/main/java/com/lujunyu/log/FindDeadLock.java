package com.lujunyu.log;

/**
 * 复现log4j1死锁。
 */

import org.apache.log4j.Logger;

/**
 * @author 58
 * @create 2018-04-28 14:40
 **/
public class FindDeadLock {
    private static Logger logger = Logger.getLogger(FindDeadLock.class);
    private long delay;
    private FindDeadLock(long delay){
        this.delay = delay;
    }
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(new FindDeadLock(100l));
            }
        }).start();
        new DeadLockingObject(5l);
    }

    public String toString(){
        new DeadLockingObject(delay);
        return super.toString();
    }

    private static class DeadLockingObject {
        private static Logger log = Logger.getLogger("test");
        private DeadLockingObject(long delay){
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("没有死锁");
        }
    }
}