package com.lujunyu.algorithm.leetcode;

import java.util.*;

/**
 * 给定一组唯一的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
 */
public class Question336 {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        Map<String, Integer> c = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            c.put(words[i], i);
        }
        for (int i=0;i<words.length;i++) {
            String word = words[i];
            if (word != null && word.length() > 0) {
                int left = 0;
                while(left<=word.length()){
                    String substring = word.substring(0, left);
                    if(isPalindrome(substring)){
                        if(left==word.length()){
                            if(c.containsKey("")){
                                result.add(Arrays.asList(c.get(""),i));
                                result.add(Arrays.asList(i,c.get("")));
                            }
                        }else{
                            String s = new StringBuilder().append(word, left, word.length()).reverse().toString();
                            if(c.containsKey(s)){
                                if(c.get(s)!=i){
                                    result.add(Arrays.asList(c.get(s),i));
                                }
                            }
                        }
                    }
                    left++;
                }
            }
        }
        return result;
    }

    private boolean isPalindrome(String word) {
        int len = word.length();
        for (int i = 0; i < len / 2; i++) {
            if (word.charAt(i) != word.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

}
