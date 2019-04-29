package com.lujunyu.currentLimiting.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TestBulkhead {

    @Test
    public void test(){
        BulkheadConfig config = BulkheadConfig.custom().maxConcurrentCalls(2).maxWaitTime(10).build();
        Bulkhead test = Bulkhead.of("test", config);
        test.isCallPermitted();
        test.isCallPermitted();
        System.out.println(Try.of( Bulkhead.decorateCheckedSupplier(test,new Service()::get)).recover(Throwable::getMessage).get());
    }

    private static class Service{
        public String get(){
            return "hello world";
        }
    }
}
