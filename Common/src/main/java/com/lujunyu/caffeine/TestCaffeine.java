package com.lujunyu.caffeine;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestCaffeine {

    /**
     * 直接使用的方式，可以灵活的控制缓存。
     */
    @Test
    public void testManual() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build();
        cache.get("key", k -> createExpensiveGraph("key"));
    }
    /**
     * Loading方式。
     */
    @Test
    public void testLoading() {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(this::createExpensiveGraph);
        System.out.println(cache.get("key"));
    }
    /**
     * 提供了一种异步获取结果的方法。
     */
    @Test
    public void testAsyn() throws ExecutionException, InterruptedException {
        AsyncCache<String,String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .buildAsync();
        CompletableFuture<String> graph = cache.get("test", k -> createExpensiveGraph("test"));
        System.out.println(graph.get());
    }


    private String createExpensiveGraph(String key) {
        return key;
    }
}
