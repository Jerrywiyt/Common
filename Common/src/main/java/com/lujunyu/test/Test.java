package com.lujunyu.test;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.crypto.Cipher;

public class Test {
  private static byte[] privateKey = "".getBytes();

  public static void main(String args[]) {
    char[] c = new char[10];
    System.out.println(c[0] == 0);
    String s = "abcd";
    System.out.println(s.substring(0, 3));
    System.out.println(s.substring(3));

    char c1 = String.valueOf(2).charAt(0);
    System.out.println(Math.abs(Integer.MIN_VALUE));

    int[] ints = new ArrayList<Integer>().stream().mapToInt(a -> a).toArray();
  }

  public static void decry() throws Exception {
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
    PrivateKey key = keyFactory.generatePrivate(spec);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, key);
  }
}
