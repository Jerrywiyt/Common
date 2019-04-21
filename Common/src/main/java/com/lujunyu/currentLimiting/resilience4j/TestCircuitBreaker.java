package com.lujunyu.currentLimiting.resilience4j;

import com.alibaba.fastjson.JSON;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Slf4j
public class TestCircuitBreaker {
    @Test
    public void testCircuitConfig() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                //可以通过这个自定义异常控制器，返回true记录，返回false不记录。
                .recordFailure(t -> {
                    if (t instanceof TimeoutException)
                        return true;
                    else
                        return false;
                })
                .build();

        CircuitBreakerRegistry REGISTRY = CircuitBreakerRegistry.of(circuitBreakerConfig);

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();// 使用默认配置创建注册器
        CircuitBreaker breaker = REGISTRY.circuitBreaker("breakName");// 通过注册器创建熔断器

        CircuitBreaker circuitBreaker = CircuitBreaker.of("test", circuitBreakerConfig);// 直接创建实例
        CircuitBreaker defaultCircuitBreaker = CircuitBreaker.ofDefaults("testName");
    }

    @Test
    public void testCircuit() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        // 第一步，将被保护的方法用函数式接口包装
        Service service = new Service();
        CheckedFunction0<String> checkedSupplier = service::doSomething;
        // 第二步，使用相应的熔断器对该函数进行装饰
        CircuitBreaker breaker = CircuitBreaker.of("test", circuitBreakerConfig);
        CheckedFunction0<String> decoratedService = CircuitBreaker.decorateCheckedSupplier(breaker, checkedSupplier);
        // 第三步，使用Vavr库的try方法执行被装饰的函数
        Try<String> result = Try.of(decoratedService);
        if (result.isSuccess()) {
            System.out.println(result.onSuccess(System.out::println));
        } else {
            System.out.println(result.onFailure(Throwable::printStackTrace));
        }
    }

    @Test
    public void testOtherMethod() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        Service service = new Service();
        CheckedFunction0<String> checkedSupplier = service::doSomething;
        CircuitBreaker breaker = CircuitBreaker.of("test", circuitBreakerConfig);
        //重置状态。
        breaker.reset();

        //熔断消费事件。
        breaker.getEventPublisher().onEvent(e -> log.info(JSON.toJSONString(e)));
        breaker.getEventPublisher()
                .onCallNotPermitted(event -> log.info(JSON.toJSONString(event)))
                .onError(event -> log.info(JSON.toJSONString(event)))
                .onReset(event -> log.info(JSON.toJSONString(event)))
                .onStateTransition(event -> log.info(JSON.toJSONString(event)))
                .onIgnoredError(event -> log.info(JSON.toJSONString(event)))
                .onSuccess(event -> log.info(JSON.toJSONString(event)));

    }

    @Test
    public void testMonitor() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        Service service = new Service();
        CheckedFunction0<String> checkedSupplier = service::doSomething;
        CircuitBreaker breaker = CircuitBreaker.of("test", circuitBreakerConfig);
        // 创建监控数据统计实例
        CircuitBreaker.Metrics metrics = breaker.getMetrics();
        // 返回失败率
        float failureRate = metrics.getFailureRate();
        // 返回当前存储的调用数目
        int bufferedCalls = metrics.getNumberOfBufferedCalls();
        // 返回当前失败调用数目
        int failedCalls = metrics.getNumberOfFailedCalls();
        // 我们也可以直接获取熔断器的状态
        breaker.getState();
        // 熔断器共五种状态
/*        DISABLED(3, false),// 熔断器不可用，允许所有调用通过
        CLOSED(0, true), // 熔断器关闭
        OPEN(1, true), // 熔断器开启
        FORCED_OPEN(4, false), // 熔断器强制开启，不允许任何调用通过，且不会自动切换到半开状态
        HALF_OPEN(2, true);// 熔断器半开*/
    }


    private static class Service {
        private String doSomething() throws Exception {
            return "hello world";
        }
    }
}
