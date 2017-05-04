package com.lujunyu.guava.base;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class TestObjects {
	public static void main(String[] args) {
		// 优雅的对象比较。
		Objects.equal(null, null);

		// 优雅的toString方法，通过flow风格可以指定输出的属性。
		MoreObjects.toStringHelper("str").add("first", "end");
		A a = new A();
		a.name = "test";
		System.out.println(a);
		// A{lala=res}

		// hashCode，可以同时计算多个属性的hashCode
		Objects.hashCode("", "");

		
		// flow式比较器，避免构建臃肿的比较方法。
	}

	static private class A implements Comparable<A> {
		private String name;
		private String err;

		public String toString() {
			return MoreObjects.toStringHelper(this).add("lala", "res").toString();
		}

		@Override
		public int compareTo(A o) {
			//flow 结构，简化了大量比较代码的开发，使代码更加优雅、简洁。
			return ComparisonChain.start()
					.compare(this.name, o.name)
					.compare(this.err, o.err)
					.result();
		}
	}
}
