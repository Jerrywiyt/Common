package com.lujunyu.juc.utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;
import org.junit.Test;

/** Promise设计模式。 */
public class TestCompletableFuture {

  @Test
  public void testCompleteFuture() {
    CompletableFuture<String> message = CompletableFuture.completedFuture("message");
    assertEquals("message", message.getNow(null));
  }

  /** runAsync表示异步执行一个runnable，其默认线程池是Fork-join common pool。 */
  @Test
  public void testAsync() {
    CompletableFuture<Void> cf =
        CompletableFuture.runAsync(
            () -> {
              assertTrue(Thread.currentThread().isDaemon());
              LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(10));
            });
    assertFalse(cf.isDone());
    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(12));
    assertTrue(cf.isDone());
  }

  @Test
  public void thenApplyExample() {
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture("message")
            .thenApply(
                s -> {
                  assertFalse(Thread.currentThread().isDaemon());
                  return s.toUpperCase();
                });
    assertEquals("MESSAGE", cf.getNow(null));
  }

  @Test
  public void thenApplyAsyncExample() {
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture("message")
            .thenApplyAsync(
                s -> {
                  assertTrue(Thread.currentThread().isDaemon());
                  LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(10));

                  return s.toUpperCase();
                });
    assertNull(cf.getNow(null));
    assertEquals("MESSAGE", cf.join());
  }

  /** 使用thenAccept可以消费CompletableFuture执行结果。 */
  @Test
  public void thenAcceptExample() {
    StringBuilder result = new StringBuilder();
    CompletableFuture.completedFuture("thenAccept message").thenAccept(s -> result.append(s));
    assertTrue("Result was empty", result.length() > 0);
  }

  @Test
  public void thenAcceptAsyncExample() {
    StringBuilder result = new StringBuilder();
    CompletableFuture<Void> cf =
        CompletableFuture.completedFuture("thenAcceptAsync message")
            .thenAcceptAsync(s -> result.append(s));
    cf.join();
    assertTrue("Result was empty", result.length() > 0);
  }

  /**
   * CompletableFuture#completeExceptionally 如果任务还未完成，则抛出指定异常 </br>
   *
   * <p>CompletableFuture#handle 可以对任务抛出的异常进行处理。
   */
  @Test
  public void completeExceptionallyExample() {
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture("message")
            .thenApplyAsync(
                s -> {
                  LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1L));
                  return s;
                });
    CompletableFuture<String> exceptionHandler =
        cf.handle(
            (s, th) -> {
              return (th != null) ? "message upon cancel" : "";
            });
    cf.completeExceptionally(new RuntimeException("completed exceptionally"));
    assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
    try {
      cf.join();
      fail("Should have thrown an exception");
    } catch (CompletionException ex) { // just for testing
      assertEquals("completed exceptionally", ex.getCause().getMessage());
    }
    assertEquals("message upon cancel", exceptionHandler.join());
  }

  /** CompletableFuture#cancel 如果completableFuture还未完成，则通过抛出异常CancellationException方式进行结束。 */
  @Test
  public void cancelExample() {
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture("message")
            .thenApplyAsync(
                s -> {
                  LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1L));
                  s = s.toUpperCase();
                  return s;
                });

    CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
    assertTrue("Was not canceled", cf.cancel(true));
    assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
    assertEquals("canceled message", cf2.join());
  }

  /** applyToEither 表示处理二者之一，谁快先用谁。 */
  @Test
  public void applyToEitherExample() {
    String original = "Message";
    CompletableFuture<String> cf1 =
        CompletableFuture.completedFuture(original).thenApplyAsync(String::toUpperCase);
    CompletableFuture<String> cf2 =
        cf1.applyToEither(
            CompletableFuture.completedFuture(original).thenApplyAsync(String::toLowerCase),
            s -> s + " from applyToEither");
    assertTrue(cf2.join().endsWith(" from applyToEither"));
  }

  /** 同上，谁快先消费谁。 */
  @Test
  public void acceptEitherExample() {
    String original = "Message";
    StringBuffer result = new StringBuffer();
    CompletableFuture<Void> cf =
        CompletableFuture.completedFuture(original)
            .thenApplyAsync(s -> delayedUpperCase(s))
            .acceptEither(
                CompletableFuture.completedFuture(original)
                    .thenApplyAsync(s -> delayedLowerCase(s)),
                s -> result.append(s).append("acceptEither"));
    cf.join();
    System.out.println(result.toString());
    assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
  }

  private static String delayedLowerCase(String s) {
    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1L));
    return s.toLowerCase();
  }

  private static String delayedUpperCase(String s) {
    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1L));
    return s.toUpperCase();
  }

  @Test
  public void runAfterBothExample() {
    String original = "Message";
    StringBuilder result = new StringBuilder();
    CompletableFuture.completedFuture(original)
        .thenApply(String::toUpperCase)
        .runAfterBoth(
            CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
            () -> result.append("done"));
    assertTrue("Result was empty", result.length() > 0);
  }

  @Test
  public void thenCombineExample() {
    String original = "Message";
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture(original)
            .thenApply(s -> delayedUpperCase(s))
            .thenCombine(
                CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s)),
                (s1, s2) -> {
                  System.out.println(Thread.currentThread().getName());
                  return s1 + s2;
                });
    assertEquals("MESSAGEmessage", cf.getNow(null));
  }

  @Test
  public void thenCombineAsyncExample() {
    String original = "Message";
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture(original)
            .thenApplyAsync(s -> delayedUpperCase(s))
            .thenCombine(
                CompletableFuture.completedFuture(original)
                    .thenApplyAsync(s -> delayedLowerCase(s)),
                (s1, s2) -> {
                  System.out.println(Thread.currentThread().getName());
                  return s1 + s2;
                });
    assertEquals("MESSAGEmessage", cf.join());
  }

  @Test
  public void thenComposeExample() {
    String original = "Message";
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture(original)
            .thenApply(s -> delayedUpperCase(s))
            .thenCompose(
                upper ->
                    CompletableFuture.completedFuture(original)
                        .thenApply(s -> delayedLowerCase(s))
                        .thenApply(s -> upper + s));
    assertEquals("MESSAGEmessage", cf.join());
  }

  /** 任何一个子任务结束就返回结果。 */
  @Test
  public void anyOfExample() {
    StringBuilder result = new StringBuilder();
    List<String> messages = Arrays.asList("a", "b", "c");
    List<CompletableFuture<String>> futures =
        messages.stream()
            .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
            .collect(Collectors.toList());
    CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()]))
        .whenComplete(
            (res, th) -> {
              if (th == null) {
                result.append(res);
              }
            });
    assertTrue("Result was empty", result.length() > 0);
  }

  /** 所有任务都完成才会结束。 */
  @Test
  public void allOfExample() {
    StringBuilder result = new StringBuilder();
    List<String> messages = Arrays.asList("a", "b", "c");
    List<CompletableFuture<String>> futures =
        messages.stream()
            .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
            .collect(Collectors.toList());
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
        .whenComplete(
            (v, th) -> {
              result.append("done");
            });
    assertTrue("Result was empty", result.length() > 0);
  }

  @Test
  public void allOfAsyncExample() {
    StringBuilder result = new StringBuilder();
    List<String> messages = Arrays.asList("a", "b", "c");
    List<CompletableFuture<String>> futures =
        messages.stream()
            .map(
                msg ->
                    CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
            .collect(Collectors.toList());
    CompletableFuture<Void> allOf =
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
            .whenComplete(
                (v, th) -> {
                  result.append("done");
                });
    allOf.join();
    assertTrue("Result was empty", result.length() > 0);
  }

  @Test
  public void test() {
    CompletableFuture<Void> voidCompletableFuture =
        CompletableFuture.completedFuture("test")
            .thenApplyAsync(
                r -> {
                  LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                  return r.toUpperCase();
                })
            .thenAccept(System.out::println);

    voidCompletableFuture.join();
  }
}
