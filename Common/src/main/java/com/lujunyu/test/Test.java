package com.lujunyu.test;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

public class Test {
  private static byte[] privateKey = "".getBytes();

  public static void main(String args[]) {
    String s = "abcd";
    System.out.println(s.substring(0, 3));
    System.out.println(s.substring(3));
  }

  public static void decry() throws Exception {
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
    PrivateKey key = keyFactory.generatePrivate(spec);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, key);
  }
}
