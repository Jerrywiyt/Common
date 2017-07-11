package com.lujunyu.utils;

/**
 * bit操作。
 * @author lujunyu
 */
public class BitUtil {
	/**
	 * 返回bit序列
	 * @param input
	 * @return
	 */
	public static String toBitString(long input){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<64;i++){
			sb.append(input&1);
			input>>=1;
		}
		return sb.reverse().toString();
	}
}
