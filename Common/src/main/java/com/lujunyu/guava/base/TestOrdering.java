package com.lujunyu.guava.base;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

public class TestOrdering {
	public static void main(String[] args) {
		
		//创造。
		//自然排序比较器。str1.compareTo(str2)
		Ordering<String> natural = Ordering.natural();
		//使用toStirng排序。s1.toString.compareTo(s2.toString)
		Ordering<Object> usingToString = Ordering.usingToString();
		//自定义比较器。
		Ordering<String> compare = new Ordering<String>(){
			@Override
			public int compare(String left, String right) {
				return 0;
			}
		};
		
		
		
		//链式api,通过类似包装的功能，丰富比较器的功能。
		Ordering.natural().nullsFirst().onResultOf(new Function<String, String>() {
			@Override
			public String apply(String input) {
				return input;
			}
		});
		
		
	}
}