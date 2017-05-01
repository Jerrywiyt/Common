package com.lujunyu.guava.cache;


import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Guava cache 小试牛刀。
 * 
 * @author lujunyu
 *
 */
public class TestCache {
	private static Logger log = Logger.getLogger(TestCache.class);
	private static LoadingCache<String,String> cache;
	static {
		try {
			cache=init();
		} catch (ExecutionException e) {
			log.error(e,e);
		}
	}

	public static void main(String[] args) throws Exception{
		String val = cache.get("key", new Callable<String>() {
			@Override
			public String call() throws Exception {
				log.info("generate key");
				// TODO 实现如果有则返回，没有则计算。
				return "guava cache";
			}
		});
		System.out.println(cache.get("key"));
		System.out.println(val);
		Thread.sleep(1000);
		cache.get("key");
	}

	private static LoadingCache<String,String> init() throws ExecutionException {
		return CacheBuilder.newBuilder()
				// 设置基于大小的回收策略。
//				.maximumSize(1000l)
			/*	.maximumWeight(100000l)
				.weigher(new Weigher<String, String>() {
					@Override
					public int weigh(String key, String value) {
						// TODO 可以指定每个value的重量，方便用来设置整个缓存的空间大小。
						return 100;
					}
				})*/
				// 设置基于时间的回收策略。
				.expireAfterAccess(10l, TimeUnit.MILLISECONDS)
				.expireAfterWrite(10000, TimeUnit.MILLISECONDS)
				// 设置基于引用的回收策略。
				// .weakKeys()
				// .weakValues()
				// .softValues()
				.ticker(new Ticker() {
					@Override
					public long read() {
						return System.nanoTime();
					}
				})
				.removalListener(new RemovalListener<String, String>() {

					@Override
					public void onRemoval(RemovalNotification<String, String> notification) {
						log.info("remove key "+notification.getKey());
					}
				})
				.build(new CacheLoader<String, String>() {

					@Override
					public String load(String key) throws Exception {
						// TODO 可以自定实现一些缓存的加载策略。
						return null;
					}

					@Override
					public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
						// TODO 重新写loadAll可以实现缓存的批量加载。
						return super.loadAll(keys);
					}

					@Override
					public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
						// TODO 可以自定义缓存的重新加载策略。
						return super.reload(key, oldValue);
					}
				});
		// 设置缓存失效
		// graphs.invalidate("");
		// graphs.invalidateAll(list);
		// graphs.invalidateAll();
	}

}
