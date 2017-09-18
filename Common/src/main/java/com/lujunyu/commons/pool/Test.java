package com.lujunyu.commons.pool;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class Test {
	public static void main(String[] args) throws Exception {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		//对象使用完，交还给队列的方式，true：放到队列的前面，false：放到队列的后面。默认为true。
		config.setLifo(false);
		//等待获取空闲连线的方式，true：公平，false：非公平，默认为false。
		config.setFairness(false);
		//连接耗尽，调用者最大等待时间。超时则直接抛异常，单位：毫秒。
		config.setMaxWaitMillis(1000l);
		//连接，最小空闲时间，超过这个时间可能会被清除。
		config.setMinEvictableIdleTimeMillis(10000000l);
		//连接空闲的最小时间，达到此值后空闲链接将会被移除，且保留“minIdle”个空闲连接数。负值(-1)表示不移除。默认值1000L 60L 30L
		config.setSoftMinEvictableIdleTimeMillis(10000l);
		//默认为3.
		config.setNumTestsPerEvictionRun(3);
		
		config.setMaxTotal(30);
		config.setMinIdle(30);
		GenericObjectPool<Connection> pool = new GenericObjectPool<>(new PooledObjectFactory<Connection>() {
			@Override
			public PooledObject<Connection> makeObject() throws Exception {
				return new DefaultPooledObject(Connection.create());
			}

			@Override
			public void destroyObject(PooledObject<Connection> p) throws Exception {
				p.getObject().close();
			}

			@Override
			public boolean validateObject(PooledObject<Connection> p) {
				return Connection.isValid(p.getObject());
			}

			@Override
			public void activateObject(PooledObject<Connection> p) throws Exception {
				System.out.println("activete obj "+ p.getObject().getId());
			}

			@Override
			public void passivateObject(PooledObject<Connection> p) throws Exception {
				System.out.println("passivate obj "+p.getObject().getId());
			}
		},config);
		
		List<Connection> temp = new ArrayList<>();
		for(int i=0;i<30;i++){
			Connection c = pool.borrowObject();
			temp.add(c);
		}
		
		for(Connection c:temp){
			pool.returnObject(c);
		}
		
		for(int i=0;i<100;i++){
			Connection c = pool.borrowObject();
			pool.returnObject(c);
		}
		
	}
	
}
