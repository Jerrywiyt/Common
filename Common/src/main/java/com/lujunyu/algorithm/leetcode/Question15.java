package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question15 {

	public List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		if(nums==null||nums.length<=3){
			return list;
		}
		
		
		for(int i=0;i<nums.length;i++){
			for(int j=i+1;j<nums.length;j++){
				for(int k=j+1;k<nums.length;k++){
					if((nums[i]+nums[j]+nums[k])==0){
						List<Integer> temp = new ArrayList<>();
						temp.add(nums[i]);
						temp.add(nums[j]);
						temp.add(nums[k]);
						list.add(temp);
					}
				}
			}
		}
		return list;
	}
	
	
	
	public List<List<Integer>> threeSum1(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		if(nums==null||nums.length<3){
			return list;
		}
		Arrays.sort(nums);
		for(int i=0;i<nums.length-2;i++){
			if(i==0||nums[i]!=nums[i-1]){
				int l = i + 1 , r = nums.length-1,num = 0 - nums[i];
				while(l<r){
					if(nums[l]+nums[r]==num){
						list.add(Arrays.asList(nums[i],nums[l],nums[r]));
						while(l<r&&nums[l]==nums[l+1])l++;
						while(l<r&&nums[r]==nums[r-1])r--;
						l++;r--;
					}else if(nums[l]+nums[r]<num){
						while(l<r&&nums[l]==nums[l+1])l++;
						l++;
					}else{
						while(l<r&&nums[r]==nums[r-1])r--;
						r--;
					}
				}
			}
		}
		return list;
	}
}
