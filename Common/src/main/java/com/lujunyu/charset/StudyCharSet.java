package com.lujunyu.charset;

/**
 * 总结几种编码的差别。
 * @author jerry
 *
 */

public class StudyCharSet {
	public static void main(String[] args) throws Exception {
		String s = "淘！我喜欢！";
		byte[] b1 = s.getBytes("utf-8");
		System.out.println(CharSetUtil.toByteString(b1));
		
		byte[] b2 = s.getBytes("gbk");
		System.out.println(CharSetUtil.toByteString(b2));
		
		byte[] b3 = s.getBytes("iso-8859-1");
		System.out.println(CharSetUtil.toByteString(b3));
		
		//utf-16 无论是字符还是汉字都采用两个字节保存。
		byte[] b4 = s.getBytes("utf-16");
		System.out.println(CharSetUtil.toByteString(b4));
	}
}
