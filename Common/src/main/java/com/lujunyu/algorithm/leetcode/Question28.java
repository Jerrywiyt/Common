package com.lujunyu.algorithm.leetcode;

/**
 * Implement strStr().
 * 
 * Return the index of the first occurrence of needle in haystack, or -1 if
 * needle is not part of haystack.
 * 
 * Example 1:
 * 
 * Input: haystack = "hello", needle = "ll" Output: 2 Example 2:
 * 
 * Input: haystack = "aaaaa", needle = "bba" Output: -1
 * 
 * @author jerry
 *
 */
public class Question28 {
	public static void main(String[] args) {
		System.out.println(strStr("fdafaabcc", "bcc"));
		System.out.println("fafadf".indexOf("fadf"));
	}
	
	/**
	 * goto语法最好不使用，用break、continue来代替。
	 * 
	 * 我自己的答案。
	 * @param haystack
	 * @param needle
	 * @return
	 */
	public static int strStr(String haystack, String needle) {
		if (haystack == null || needle == null || needle.length() > haystack.length()) {
			return -1;
		}
		for (int i = 0; ; i++) {
			for (int j = 0; ; j++) {
				if(j == needle.length()){
					return i;
				}
				if(i + j == haystack.length()){
					return -1;
				}
				if (haystack.charAt(i + j) != needle.charAt(j)) {
					break;
				}
			}
		}
	}
}
