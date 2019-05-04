package com.lujunyu.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.junit.Test;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * 三种缓存过期策略
 */
public class TestEviction {
    @Test
    public void testSize() {
        LoadingCache<String, String> graphs1 = Caffeine.newBuilder()
                .maximumSize(10_000)
                .build(this::load);

        // Evict based on the number of vertices in the cache
        LoadingCache<String, String> graphs2 = Caffeine.newBuilder()
                .maximumWeight(10_000)
                .weigher((String key, String graph) -> graph.length())
                .build(this::load);
    }


    @Test
    public void testTime() {
        // Evict based on a fixed expiration policy
        LoadingCache<Key, Graph> graphs1 = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));
        LoadingCache<Key, Graph> graphs2 = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));

// Evict based on a varying expiration policy
        LoadingCache<Key, Graph> graphs = Caffeine.newBuilder()
                .expireAfter(new Expiry<Key, Graph>() {
                    public long expireAfterCreate(Key key, Graph graph, long currentTime) {
                        // Use wall clock time, rather than nanotime, if from an external resource
                        long seconds = graph.creationDate().plusHours(5)
                                .minus(System.currentTimeMillis(), ChronoUnit.MILLIS).getSeconds();
                        return TimeUnit.SECONDS.toNanos(seconds);
                    }
                    public long expireAfterUpdate(Key key, Graph graph,
                                                  long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                    public long expireAfterRead(Key key, Graph graph,
                                                long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                })
                .build(key -> createExpensiveGraph(key));
    }

    @Test
    public void testReffer(){
        // Evict when neither the key nor value are strongly reachable
        LoadingCache<Key, Graph> graphs = Caffeine.newBuilder()
                .weakKeys()
                .weakValues()
                .build(key -> createExpensiveGraph(key));

// Evict when the garbage collector needs to free memory
        LoadingCache<Key, Graph> graphs1 = Caffeine.newBuilder()
                .softValues()
                .build(key -> createExpensiveGraph(key));
    }

    private String load(String key) {
        return key + "1";
    }

    private Graph createExpensiveGraph(Key key){
        return new Graph();
    }
}
