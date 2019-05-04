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
        // Lookup an entry, or null if not found
        String graph = cache.getIfPresent("test");
        // Lookup and compute an entry if absent, or null if not computable
        graph = cache.get("test", k -> createExpensiveGraph("test"));
        // Insert or update an entry
        cache.put("test", graph);
        // Remove an entry
        cache.invalidate("test");
    }

    private String createExpensiveGraph(String key) {
        return key;
    }

    @Test
    public void testLoading() {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));

        // Lookup and compute an entry if absent, or null if not computable
        String graph = cache.get("test");
        // Lookup and compute entries that are absent
        Map<String, String> graphs = cache.getAll(Lists.newArrayList());
    }

    /**
     * 提供了一种异步获取结果的方法。
     */
    @Test
    public void testAsyn() throws ExecutionException, InterruptedException {
        AsyncCache<String,String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .executor(Executors.newFixedThreadPool(1))
                .buildAsync();

        CompletableFuture<String> graph = cache.get("test", k -> createExpensiveGraph("test"));
        System.out.println(graph.get());
    }
}
