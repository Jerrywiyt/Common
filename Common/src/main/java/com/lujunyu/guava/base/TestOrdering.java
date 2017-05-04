package com.lujunyu.guava.base;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Ordering;

public class TestOrdering {
	public static void main(String[] args) {
		Ordering<String> order = new Ordering<String>(){
			@Override
			public int compare(String left, String right) {
				return left.compareTo(right);
			}
			
		};
		List<String> list = new ArrayList<String>();
		list.add("c");
		list.add("a");
		list.add("b");
		
		//排序list，并且进行copy
		List<String> sortedList = order.sortedCopy(list);
		System.out.println(list);
		System.out.println(sortedList);
		
		
	}
}