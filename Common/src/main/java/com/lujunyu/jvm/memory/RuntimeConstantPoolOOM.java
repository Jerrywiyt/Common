package com.lujunyu.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args:-XX:Permsize=10M -XX:MaxPermSize=10M
 * @author jerry
 * 
 * jre1.6
 * 
 * 方法区的内存溢出测试
 * 
 * 1.6方法区属于永久代，方便垃圾回收，1.7开始要逐步去除永久代。
 *
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i=0;
		while(true){
			list.add(String.valueOf(i++).intern());
		}
	}

}
