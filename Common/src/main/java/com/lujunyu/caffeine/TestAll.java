package com.lujunyu.caffeine;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TestAll {
  /**
   * Caffeine的reload不同于Guava
   * cache，它本身就是异步的，默认线程池是ForkJoinPool.commonPool，可以通过executor(Executor)方法进行指定线程池。guava
   * cache的异步需要在reload方法中指定线程池。
   */
  @Test
  public void testRefresh() {
    LoadingCache<String, String> cache =
        Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .refreshAfterWrite(2, TimeUnit.SECONDS)
            .executor(Executors.newFixedThreadPool(1))
            .build(
                new CacheLoader<String, String>() {
                  @Override
                  public String load(@NonNull String s) throws Exception {
                    log.info("load");
                    return "new";
                  }

                  @Override
                  public String reload(@NonNull String key, @NonNull String oldValue) {
                    log.info(Thread.currentThread().getName());
                    return "reload";
                  }
                });

    System.out.println(cache.get("test"));
    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3));
    System.out.println(cache.get("test"));
    System.out.println(cache.get("test"));
    LockSupport.park();
  }

  /** writer就是把缓存的写入操作和过期操作通过事件的方式暴露出来，guava cache应该也有类似的方法。 */
  @Test
  public void testWriter() {
    LoadingCache<String, String> cache =
        Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .executor(Executors.newFixedThreadPool(1))
            .writer(
                new CacheWriter<String, String>() {

                  @Override
                  public void write(@NonNull String s, @NonNull String s2) {
                    log.info(String.format("%s-%s", s, s2));
                  }

                  @Override
                  public void delete(
                      @NonNull String s, @Nullable String s2, @NonNull RemovalCause removalCause) {
                    log.info(String.format("%s-%s-%s", s, s2, removalCause));
                  }
                })
            .build(
                s -> {
                  log.info("load");
                  return "new";
                });

    System.out.println(cache.get("test"));
    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
    System.out.println(cache.get("test"));
    cache.put("test1", "test1");
  }

  /** guava cache提供了类似的功能。 */
  @Test
  public void testStatis() {
    LoadingCache<String, String> cache =
        Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .executor(Executors.newFixedThreadPool(1))
            .recordStats()
            .writer(
                new CacheWriter<String, String>() {

                  @Override
                  public void write(@NonNull String s, @NonNull String s2) {
                    log.info(String.format("%s-%s", s, s2));
                  }

                  @Override
                  public void delete(
                      @NonNull String s, @Nullable String s2, @NonNull RemovalCause removalCause) {
                    log.info(String.format("%s-%s-%s", s, s2, removalCause));
                  }
                })
            .build(
                s -> {
                  log.info("load");
                  return "new";
                });

    System.out.println(cache.get("test"));
    System.out.println(cache.get("test"));
    System.out.println(cache.get("test"));
    System.out.println(cache.get("test"));
    cache.put("test1", "test1");
    @NonNull CacheStats stats = cache.stats();
    System.out.println(stats.hitRate());
  }

  /** 与guava cache不同，guava cache的清除操作都是同步进行的，而caffeine是异步执行的，但是它们都是通过read/write来触发evict操作。 */
  @Test
  public void testCleanUp() {
    LoadingCache<String, String> cache =
        Caffeine.newBuilder()
            .maximumSize(100)
            .executor(Executors.newFixedThreadPool(1))
            .recordStats()
            .writer(
                new CacheWriter<String, String>() {

                  @Override
                  public void write(@NonNull String s, @NonNull String s2) {
                    log.info(String.format("%s-%s", s, s2));
                  }

                  @Override
                  public void delete(
                      @NonNull String s, @Nullable String s2, @NonNull RemovalCause removalCause) {
                    log.info(String.format("%s-%s-%s", s, s2, removalCause));
                  }
                })
            .build(
                s -> {
                  log.info("load");
                  return "new";
                });

    for (int i = 0; i < 200; i++) {
      cache.get(i + "");
    }
    LockSupport.park();
  }

  public void testAll() {
    LoadingCache<String, String> cache =
        Caffeine.newBuilder()
            // 基于数量的配置
            .maximumSize(1000)
            // 基于权重
            .maximumWeight(1000)
            // 指定计算权重的方式
            .weigher(this::caculateWeight)
            // 指定缓存在写入多久后失效。
            .expireAfterWrite(1000, TimeUnit.SECONDS)
            // 指定缓存在访问多久后失效。
            .expireAfterAccess(1000, TimeUnit.SECONDS)
            .build(this::load);
  }

  private int caculateWeight(String s, String s2) {
    return s2.length();
  }

  private String load(String s) {
    return s;
  }
}
