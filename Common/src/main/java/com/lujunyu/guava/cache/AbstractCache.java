package com.lujunyu.guava.cache;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.lujunyu.threadpool.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用此缓存一定要注意线程安全问题。
 * expireAfterWrite优先于refreshAfterWrite判断
 * refreshAfterWrite < t < expireAfterWrite 时异步reload。
 * @param <K>
 * @param <V>
 */
@Slf4j
public abstract class AbstractCache<K,V> {
    private CacheConfig config = getConfig();
    private static ThreadPoolExecutor executor = getExecutor();

    private final LoadingCache<K,Optional<V>> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(config.getMaximumSize())
            //超过指定时间后，同步去读取新值，并覆盖旧值。
            .expireAfterWrite(config.getExpireAfterWrite(),TimeUnit.SECONDS)
            //超过指定时间后，调用reload异步加载，并返回旧值。
            .refreshAfterWrite(config.getRefreshAfterWrite(),TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<K,Optional<V>>() {
                @Override
                public Optional<V> load(K k){
                    if(log.isDebugEnabled()){
                        log.debug("同步加载数据 class="+AbstractCache.this.getClass().getSimpleName()+" k="+k);
                    }
                    return loadData(k);
                }

                @Override
                public ListenableFuture<Optional<V>> reload(K key, Optional<V> oldValue) throws Exception {
                    if(log.isDebugEnabled()){
                        log.debug("reload Key={} oldValue={}",key,oldValue);
                    }
                    ListenableFutureTask<Optional<V>> task = ListenableFutureTask.create(()->loadData(key));
                    executor.execute(task);
                    return task;
                }

                private Optional<V> loadData(K k){
                    try{
                        return Optional.fromNullable(loadCache(k));
                    }catch (Exception e){
                        log.error(e.getMessage(),e);
                    }
                    return Optional.absent();
                }
            });

    protected abstract V loadCache(K k);

    protected abstract CacheConfig getConfig();
    /**
     * 获取缓存的对象。
     * 注意对象可能被多个线程修改，造成错误。
     * 可以通过重写此函数拷贝一个新的对象避免此类问题。
     */
    public V get(K k){
        Optional<V> optional;
        try {
            optional = loadingCache.get(k);
            if(optional!=null&&optional.isPresent()){
                return optional.get();
            }else if(!config.isCacheNull()){
                optional = Optional.fromNullable(loadCache(k));
                if(optional.isPresent()){
                    loadingCache.put(k,optional);
                    return optional.get();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void monitor(){
    }





    private static ThreadPoolExecutor getExecutor() {
        return new ThreadPoolExecutor(200,
                200,
                300,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(30000),
                new NamedThreadFactory("cache-reload"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    static{
        monitorMyThreadPool();
    }

    private static void monitorMyThreadPool(){

        Thread monitorMyThreadPool = new Thread(() -> {

            long taskCount = 0;
            long completedTaskCount = 0;
            long noCompletedTaskCount = 0;

            int thisNum = 0;

            while(true){
                taskCount = executor.getTaskCount();
                completedTaskCount = executor.getCompletedTaskCount();
                noCompletedTaskCount = taskCount - completedTaskCount;

                if( noCompletedTaskCount > 10 || thisNum%300 == 299){
                    log.info("二级业务：总数="+ taskCount
                            + " 已完成="+ completedTaskCount
                            + " 未完成="+ noCompletedTaskCount
                            + " activeCount="+ executor.getActiveCount()
                            + " LargestPoolSize="+ executor.getLargestPoolSize()
                            + " PoolSize="+ executor.getPoolSize());

                    thisNum = 0;
                }
                thisNum ++;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        });
        monitorMyThreadPool.setName("缓存监控");
        monitorMyThreadPool.start();
    }
}