package com.lujunyu.rt;

import java.util.Base64;

public class TestBase64 {
    public static void main(String args[]){
        System.out.println(Base64.getEncoder().encodeToString("123456789".getBytes()));
    }
}
