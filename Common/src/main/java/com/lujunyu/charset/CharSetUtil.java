package com.lujunyu.charset;

public class CharSetUtil {
  public static String toByteString(byte[] bys) {
    if (bys == null || bys.length < 1) return null;
    StringBuffer sb = new StringBuffer(100);
    for (byte b : bys) {
      if (b >= 16) sb.append(Integer.toHexString(b));
      else if (b >= 0) sb.append("0" + Integer.toHexString(b));
      else sb.append(Integer.toHexString(b).substring(6, 8));
      sb.append(" ");
    }

    return sb.toString();
  }
}
