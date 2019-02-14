package com.lujunyu.algorithm.similar;

import org.apache.commons.text.similarity.*;
import org.junit.Test;

/**
 * commons-text包中提供的相似度算法。
 */
public class TestCommons {
    private static String text1 = "今天天气不好啊";
    private static String text2 = "今天天气不错呀";

    @Test
    public void testJaroWinklerDistance(){
        System.out.println(new JaroWinklerDistance().apply(text1,text2));
    }

    @Test
    public void testJaccardDistance(){
        System.out.println(new JaccardDistance().apply(text1,text2));
    }

    @Test
    public void testLevenshteinDistance(){
        System.out.println(new LevenshteinDistance().apply(text1,text2));
        System.out.println(new LevenshteinDetailedDistance().apply(text1,text2));
        System.out.println(1-(double)new LevenshteinDistance().apply(text1,text2)/ text1.length());
    }

    @Test
    public void testHammingDistance(){
        System.out.println(new HammingDistance().apply(text1,text2));
    }
    @Test
    public void testLongestCommonSubsequenceDistance(){
        System.out.println(new LongestCommonSubsequenceDistance().apply(text1,text2));
    }

    @Test
    public void testCosineDistance(){
        System.out.println(new CosineDistance().apply(text1,text2));
    }
}
