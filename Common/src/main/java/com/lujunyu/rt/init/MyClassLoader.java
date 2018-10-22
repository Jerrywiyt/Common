package com.lujunyu.rt.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MyClassLoader extends ClassLoader{
    public static void main(String args[]){
//        ClassLoader.getSystemClassLoader();
        JSONObject obj = new JSONObject();
        obj.put("a","ff");
        obj.put("b","aaa\t");

        System.out.println(obj.toJSONString());

        System.out.println(JSON.parseObject(obj.toJSONString()));

        System.out.println("aaa\taaa".replaceAll("\t",""));
    }
}
