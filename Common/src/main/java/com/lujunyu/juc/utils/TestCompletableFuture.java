package com.lujunyu.juc.utils;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Promise设计模式。
 */
public class TestCompletableFuture {
    @Test
    public void testVoid() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->System.out.println("Hello world"));
        completableFuture.get();
    }

    @Test
    public void testReturn() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->"hello world");
        System.out.println(completableFuture.get());
    }
    @Test
    public void testThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->"hello world")
                .thenApply(String::toUpperCase);
        System.out.println(completableFuture.get());
    }

    @Test
    public void testThenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(()->"hello world")
                .thenAccept(s -> System.out.println("res ： " + s));
        System.out.println(completableFuture.get());
    }

    @Test
    public void testCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(()->"hello");
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(()->"world");

        CompletableFuture<String> combineRes = completableFuture1.thenCombine(completableFuture2,(s1,s2)->s1+" "+s2).thenApply(String::toUpperCase);

        System.out.println(combineRes.get());
    }
}
