package com.lujunyu.jvm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

/**
 * 1,6 1.7 VM args: -XX:PermSize=10M,-XX:MaxPermSize=10M
 * 1.8 VM args: -XX:MetaspaceSize=10M,-XX:MaxMetaspaceSize=10M
 * @author jerry
 * 测试方法区内存溢出场景。
 */
public class JavaMethodAreaOOM {
	public static void main(String[] args) {
		while(true){
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(HeadOOM.OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {

				@Override
				public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
					return arg3.invokeSuper(arg0, arg2);
				}
			});
			enhancer.create();
		}
	}
}
