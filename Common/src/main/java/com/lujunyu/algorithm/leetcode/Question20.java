package com.lujunyu.algorithm.leetcode;

import java.util.LinkedList;

public class Question20 {

  public static void main(String[] args) {
    System.out.println(isValid("()[{}]"));
    System.out.println(isValid("({}[)]"));
  }

  public static boolean isValid(String s) {
    if (s == null) {
      return false;
    }
    char[] arr = s.toCharArray();
    LinkedList<Character> stack = new LinkedList<>();
    for (char c : arr) {
      switch (c) {
        case '(':
        case '{':
        case '[':
          stack.push(c);
          break;
        case ')':
          if (stack.size() == 0 || '(' != stack.pop()) {
            return false;
          }
          break;
        case '}':
          if (stack.size() == 0 || '{' != stack.pop()) {
            return false;
          }
          break;
        case ']':
          if (stack.size() == 0 || '[' != stack.pop()) {
            return false;
          }
      }
    }
    return stack.size() <= 0;
  }
}
