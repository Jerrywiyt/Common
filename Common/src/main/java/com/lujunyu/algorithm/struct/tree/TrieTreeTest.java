package com.lujunyu.algorithm.struct.tree;

import org.junit.Test;

import java.util.Arrays;

public class TrieTreeTest {

    @Test
    public void testDoubleArrayTrie(){
        DoubleArrayTrie doubleArrayTrie = new DoubleArrayTrie();
        doubleArrayTrie.build(Arrays.asList("abc","fad","fwwd"));
        System.out.println(doubleArrayTrie.exactMatchSearch("abc"));
        System.out.println(doubleArrayTrie.exactMatchSearch("fwwd"));
    }
}
