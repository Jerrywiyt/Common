package com.lujunyu.jvm.random;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TestRandom {

    @Test
    public void testSecureRandom() throws NoSuchAlgorithmException {
//        SecureRandom secureRandom = new SecureRandom();
//        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");
        System.out.println(secureRandom.getAlgorithm());
        System.out.println(Hex.encodeHexString(secureRandom.generateSeed(10)));
        System.out.println(secureRandom.nextInt());
    }
}
