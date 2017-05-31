package com.lujunyu.id;

import java.util.HashMap;
import java.util.Map;

/***
 * 通过结合自身业务，改进snowflake算法，来生成全局唯一id。
 * 
 * @author jerry
 *
 */
public class SeqIdGenerator implements IdHelper{

	@Override
	public long nextId() {
		return 0;
	}

	@Override
	public long nextId(Object source) {
		return 0;
	}
	
	
	public static void main(String[] args) {
		
	}
}
