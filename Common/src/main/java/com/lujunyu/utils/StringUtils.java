package com.lujunyu.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.google.common.base.Preconditions;

/**
 * 字符串处理操作
 *
 * @author lujunyu
 */
public final class StringUtils {
  private StringUtils() {}

  /**
   * 是否为空
   *
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    return str == null || str.trim().length() == 0 ? true : false;
  }

  /**
   * 是否不为空
   *
   * @param str
   * @return
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  /**
   * 基本类型转换。
   *
   * @param str
   * @param clazz
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T stringToClass(String str, Class<T> clazz) {
    if (clazz.equals(Byte.class)) {
      return (T) Byte.valueOf(str.trim());
    } else if (clazz.equals(Short.class)) {
      return (T) Short.valueOf(str.trim());
    } else if (clazz.equals(Integer.class)) {
      return (T) Integer.valueOf(str.trim());
    } else if (clazz.equals(Long.class)) {
      return (T) Long.valueOf(str.trim());
    } else if (clazz.equals(Float.class)) {
      return (T) Float.valueOf(str.trim());
    } else if (clazz.equals(Double.class)) {
      return (T) Double.valueOf(str.trim());
    } else if (clazz.equals(Character.class)) {
      return (T) Character.valueOf(str.trim().toCharArray()[0]);
    } else if (clazz.equals(String.class)) {
      return (T) str.trim();
    }
    return null;
  }

  /**
   * 将汉字转换为指定编码的十六进制的字符串。
   *
   * @param str
   * @return
   */
  public static String convertToHex(String str, String charsetName) {
    byte[] resByte = null;
    String resHex = null;
    try {
      resByte = str.getBytes(charsetName);
      resHex = byte2hex(resByte);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return resHex;
  }

  /**
   * 字节数组转为十六进制字符串
   *
   * @param b
   * @return
   */
  public static final String byte2hex(byte b[]) {
    Preconditions.checkNotNull(b, "Argument must not be null");
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = Integer.toHexString(b[n] & 0xff);
      if (stmp.length() == 1) {
        hs = hs + "0" + stmp;
      } else {
        hs = hs + stmp;
      }
    }
    return hs.toUpperCase();
  }

  /**
   * 十六进制串转化为byte数组
   *
   * @return the array of byte
   */
  public static final byte[] hex2byte(String hex) {
    Preconditions.checkArgument((isNotEmpty(hex) && hex.length() % 2 == 0), "Argument is error");
    char[] arr = hex.toCharArray();
    byte[] b = new byte[hex.length() / 2];
    for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
      String swap = "" + arr[i++] + arr[i];
      int byteint = Integer.parseInt(swap, 16) & 0xFF;
      b[j] = new Integer(byteint).byteValue();
    }
    return b;
  }
}
