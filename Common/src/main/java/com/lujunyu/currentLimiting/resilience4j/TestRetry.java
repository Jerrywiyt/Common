package com.lujunyu.currentLimiting.resilience4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class TestRetry {
    @Test
    public void test(){
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(2)
                .waitDuration(Duration.ofMillis(100)).build();

        Retry test = Retry.of("test", config);

        System.out.println(Try.of(Retry.decorateCheckedSupplier(test,new Service()::get)).recover(Throwable::getMessage).get() );
    }

    private static class Service{
        public String get() throws Exception {
            System.out.println("diaoyong");
            if(ThreadLocalRandom.current().nextInt(6)%2==0){
                throw new Exception("fail");
            }
            return "hello world";
        }
    }
}
