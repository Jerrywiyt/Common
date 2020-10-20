package com.lujunyu.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

public class TestRemoval {
  @Test
  public void test() {
    @NonNull
    Cache<String, String> cache =
        Caffeine.newBuilder()
            .maximumSize(100)
            .removalListener(
                (String key, String graph, RemovalCause cause) ->
                    System.out.printf("Key %s was removed (%s)%n", key, cause))
            .build();
  }
}
