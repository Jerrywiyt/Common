package com.lujunyu.test;


import java.io.UnsupportedEncodingException;

public class Test {
    String i = "\003";
    public static void main(String args[]) throws UnsupportedEncodingException {
        System.out.println(new String(new byte[]{-88,-97,74,-63,51,-40,-91},"ISO-8859-1"));
        byte[] bytes = "\"".getBytes();
        System.out.println("");
    }
}
