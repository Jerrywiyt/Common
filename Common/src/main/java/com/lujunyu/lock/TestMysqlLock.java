package com.lujunyu.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestMysqlLock {

    /**
     * 不断的重试。
     */
    public boolean tryLock(long resourceId,long time){
        while(true){
            long endTime = System.currentTimeMillis() + time;
            if(accquire(resourceId)){
                return true;
            }
            if(endTime>System.currentTimeMillis()){
                return false;
            }
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }
    }

    /**
     * 重试
     */
    public boolean lock(long resourceId){
        while(true){
            if(accquire(resourceId)){
                return true;
            }
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }
    }



    /**
     * TODO 待实现。
     * 事务版本
     */
    private boolean accquire(long resourceId){
        String id =  getLocalIp()+ "_"+ Thread.currentThread().getId();
        String sql = "select * from tb_lock where resource_id=" + resourceId;
        return true;
    }

    private boolean release(long resourceId){
        return true;
    }

    private String getLocalIp() {
        return "";
    }
}
