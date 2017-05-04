package com.lujunyu.guava.optional;

import com.google.common.base.Optional;

public class TestOptional {
	public static void main(String[] args) {
		//返回非空引用，参数与非空。
		Optional<String> present = Optional.of("str");
		//返回null引用
		Optional<String> absent = Optional.absent();
		
		if(present.isPresent()){
			System.out.println(present.get());
		}
		if(absent.isPresent()){
			System.out.println(absent.get());
		}
		//如果T引用不为null则返回T引用，如果为空则返回默认值，如果传入的默认值为空则抛异常。
		System.out.println(absent.or("fad"));
		//可以传入空参数。
		Optional<String> absent1 = Optional.fromNullable(null);
		//是上一个方法的逆过程。
		System.out.println(absent1.orNull());
	}
}
