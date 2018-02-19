package com.lujunyu.algorithm.leetcode;

/**
 * Given a sorted array, remove the duplicates in-place such that each element
 * appear only once and return the new length.
 * 
 * Do not allocate extra space for another array, you must do this by modifying
 * the input array in-place with O(1) extra memory.
 * 
 * Example:
 * 
 * Given nums = [1,1,2],
 * 
 * Your function should return length = 2, with the first two elements of nums
 * being 1 and 2 respectively. It doesn't matter what you leave beyond the new
 * length.
 * 
 * @author jerry
 *
 */
public class Question26 {
	public static void main(String[] args) {
		System.out.println(removeDuplicates(new int[]{1,1,2}));
		System.out.println(removeDuplicates(new int[]{1,2,3,4,4,5,6,6}));

	}

	public static int removeDuplicates(int[] nums) {
		if(nums==null||nums.length==0)
			return 0;
		int len = 1;
		for(int i=1;i<nums.length;i++){
			if(nums[i] != nums[len-1]){
				len++;
				nums[len-1] = nums[i];
			}
		}
		return len;
	}
}
