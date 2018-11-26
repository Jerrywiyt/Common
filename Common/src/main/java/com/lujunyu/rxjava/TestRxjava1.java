package com.lujunyu.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TestRxjava1 {


    /**
     * 在Rxjava中，Observable作为可观察的对象的，Observer作为观察者，Subscirber是Observer的子类。
     *
     * Observable通过create方法进行初始化，通过Observable.onSubscirbe类进行事件编排。
     *
     * Observer通过Observable的subscribe方法进行事件的订阅，同时触发事件开始。
     *
     * 以上就是Rxjava的基本组成。
     */
    @Test
    public void helloword(){
        Observable<String> observable = Observable.create(subscriber -> {
            subscriber.onNext("hello1");
            subscriber.onNext("hello2");
            subscriber.onNext("hello3");
            subscriber.onCompleted();
        });

        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });

        Observable.from(new String[]{"1","2"})
                .subscribe(System.out::println);
        Observable.just("a","b","c")
                .subscribe(System.out::println);
    }

    /**
     * Scheduler线程调度器，可以指定生产者和消费者在哪个线程执行。
     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
     * 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，
     * 可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中
     * ，可以避免创建不必要的线程。
     * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，
     * 即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，
     * 大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     *
     *
     * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。
     * 或者叫做事件产生的线程。
     * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
     */
    @Test
    public void testScheduler(){
        Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            subscriber.onNext("1");
            subscriber.onNext("2");
            subscriber.onNext("3");
            System.out.println(Thread.currentThread().getName());
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(s -> System.out.println(Thread.currentThread().getName()));

    }
    
    @Test
    public void testScheduler1(){
        Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            System.out.println(Thread.currentThread().getName());
            subscriber.onNext("1");
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.newThread())
                .map((Func1<String, String>) s -> {
                    System.out.println(Thread.currentThread().getName());
                    return s;
                })
                .subscribe(System.out::println);

    }


    /**
     * map转换
     */
    @Test
    public void testMap(){
        Observable.just("1","2","3")
                .map(Integer::parseInt)
                .subscribe(i -> System.out.println(i+1));
    }

    /**
     * 变换有点类似代理，把结果进行转换然后重新发出。
     */
    @Test
    public void testFlatMat(){
        Observable.just("1,2,3","4,5,6","7,8,9")
                .flatMap((Func1<String, Observable<String>>) s -> Observable.from(s.split(",")))
                .subscribe(System.out::println);
    }


}
