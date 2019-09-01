package com.lujunyu.jvm.basic;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class TestArrayList {


    /**
     * 返回list的差集
     */
    @Test
    public void testRemoveAll(){
        List<Integer> list1 = Lists.newArrayList(1,2,3);
        List<Integer> list2 = Lists.newArrayList(1,2);
        System.out.println(list1.removeAll(list2));
        System.out.println(list1);
    }

    /**
     * 返回两个列表的交集。
     */
    @Test
    public void testRetainAll(){
        List<Integer> list1 = Lists.newArrayList(1,2,3);
        List<Integer> list2 = Lists.newArrayList(1,2);
        System.out.println(list1.retainAll(list2));
        System.out.println(list1);
    }


}
