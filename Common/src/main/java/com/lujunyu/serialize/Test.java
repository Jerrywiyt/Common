package com.lujunyu.serialize;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Test {
    private static int n = 10000;

    public static void main(String args[]){
        Person p = new Person();
        p.setMan(true);
        p.setName("jerry");
        p.setHeight(185);
        Map<String,String> maps = Maps.newHashMap();
        maps.put("111","111");
        p.setExtend(maps);

        benchmark(p, new SerializeHandler<Person>() {
            @Override
            public byte[] serialize(Person person) {
                return JdkSerializeUtil.serialize(person);
            }

            @Override
            public Person unserialize(byte[] b, Class<Person> clazz) {
                return JdkSerializeUtil.unserialize(b,clazz);
            }
        });

        benchmark(p, new SerializeHandler<Person>() {
            @Override
            public byte[] serialize(Person person) {
                return ProtostuffUtil.serialize(p);
            }

            @Override
            public Person unserialize(byte[] b, Class<Person> clazz) {
                return ProtostuffUtil.deserialize(b,clazz);
            }
        });


        benchmark(p, new SerializeHandler<Person>() {
            @Override
            public byte[] serialize(Person person) {
                try {
                    return JSON.toJSONString(person).getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Person unserialize(byte[] b, Class<Person> clazz) {
                try {
                    return JSON.parseObject(new String(b,"utf-8"),clazz);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });


    }


    private static void benchmark(Person p,SerializeHandler<Person> handler){
        byte[] b = null;
        long t1 = System.currentTimeMillis();
        for(int i=0;i<n;i++){
            b = handler.serialize(p);
        }
        long t2 = System.currentTimeMillis();
        for(int i=0;i<n;i++){
            handler.unserialize(b,Person.class);
        }
        long t3 = System.currentTimeMillis();
        System.out.println(String.format(String.format("长度：%s，序列化时间：%s，反序列化时间：%s",b.length,(t2-t1),(t3-t2))));
    }


    private static interface SerializeHandler<T>{
        byte[] serialize(T t);
        T unserialize(byte[] b,Class<T> clazz);
    }
}
