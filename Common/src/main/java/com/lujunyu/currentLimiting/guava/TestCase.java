package com.lujunyu.currentLimiting.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

public class TestCase {
    /**
     * Guava提供的令牌桶算法。
     */
    @Test
    public void testLimiting(){
        RateLimiter rateLimiter = RateLimiter.create(1);
        for(int i=0;i<10;i++){
            double acquire = rateLimiter.acquire();
            System.out.println("waitTime:"+acquire);
        }
    }
}
