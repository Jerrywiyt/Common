package com.lujunyu.currentLimiting.resilience4j;

import com.alibaba.fastjson.JSON;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.substwsdl.TImport;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TestTimeLimiter {

    @Test
    public void test(){
        //timeoutDuration 表示多久超时，
        //cancelRunningFuture 表示超时后是否中断运行的线程。
        TimeLimiter timeLimiter = TimeLimiter.of(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(500)).cancelRunningFuture(false).build());

        Callable<String> callable = TimeLimiter.decorateFutureSupplier(timeLimiter,()->Executors.newFixedThreadPool(1).submit(() -> new Service().get()));

        Try<String> result = Try.of(callable::call);

        if(result.isSuccess()){
            System.out.println(result.get());
        }else{
            result.failed().get().printStackTrace();
        }
    }

    @Test
    public void testWithCircuitBreaker(){
        TimeLimiter timeLimiter = TimeLimiter.of(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(500)).cancelRunningFuture(true).build());
        Callable<String> callable = TimeLimiter.decorateFutureSupplier(timeLimiter,()->Executors.newFixedThreadPool(1).submit(()->new Service().get()));

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(2)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(3)
                .ringBufferSizeInClosedState(3)
                .build();
        CircuitBreaker breaker = CircuitBreaker.of("test",circuitBreakerConfig);
        breaker.getEventPublisher().onEvent(e->log.info(JSON.toJSONString(e)));

        Callable<String> chainedCallable = CircuitBreaker.decorateCallable(breaker,callable);
        Try.of(chainedCallable::call).recover(t->"异常"+t.getMessage()).onSuccess(r->log.info("结果："+r));
        Try.of(chainedCallable::call).recover(t->"异常"+t.getMessage()).onSuccess(r->log.info("结果："+r));
        Try.of(chainedCallable::call).recover(t->"异常"+t.getMessage()).onSuccess(r->log.info("结果："+r));
        Try.of(chainedCallable::call).recover(t->"异常"+t.getMessage()).onSuccess(r->log.info("结果："+r));
        Try.of(chainedCallable::call).recover(t->"异常"+t.getMessage()).onSuccess(r->log.info("结果："+r));
        Try.of(chainedCallable::call).recover(t->"异常"+t.getMessage()).onSuccess(r->log.info("结果："+r));
        Try.of(chainedCallable::call).recover(t->"异常"+t.getMessage()).onSuccess(r->log.info("结果："+r));
    }
    private static class Service{

        private String get(){
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1001));
            log.info("运行结束");
            return "hello world";
        }
    }
}
