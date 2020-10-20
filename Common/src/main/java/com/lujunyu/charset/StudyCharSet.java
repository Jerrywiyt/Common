package com.lujunyu.charset;

import org.junit.Test;

import java.io.*;

/**
 * 总结几种编码的差别。
 *
 * @author jerry
 */
public class StudyCharSet {
  public static void main(String[] args) throws Exception {
    String s = "淘！我喜欢！";
    byte[] b1 = s.getBytes("utf-8");
    System.out.println(CharSetUtil.toByteString(b1));

    byte[] b2 = s.getBytes("gbk");
    System.out.println(CharSetUtil.toByteString(b2));

    byte[] b3 = s.getBytes("iso-8859-1");
    System.out.println(CharSetUtil.toByteString(b3));

    // utf-16 无论是字符还是汉字都采用两个字节保存。
    byte[] b4 = s.getBytes("utf-16");
    System.out.println(CharSetUtil.toByteString(b4));
  }

  @Test
  public void testUtf16() throws UnsupportedEncodingException {
    String text = "\uD83D\uDE18";

    System.out.println(text);
    System.out.println(text.length());

    System.out.println(CharSetUtil.toByteString(text.getBytes("utf-8")));
    System.out.println(CharSetUtil.toByteString(text.getBytes("utf-16")));

    // 辅助平面字符的处理

    // 返回字符串中unicode码点的个数
    System.out.println(text.codePointCount(0, text.length()));

    // 返回指定索引的码点
    System.out.println(Character.codePointAt(text.toCharArray(), 0));
  }

  @Test
  public void test() {
    System.out.println(Character.toChars(128536));
    System.out.println("\uD83D\uDE18");
  }

  @Test
  public void testFile() throws IOException {
    BufferedReader br =
        new BufferedReader(
            new InputStreamReader(
                new FileInputStream(new File("D:\\Users\\lujunyu\\Desktop\\aaaa.txt")), "utf-8"));
    String line = br.readLine();

    char[] chars = line.toCharArray();

    System.out.println(CharSetUtil.toByteString(line.getBytes("utf-8")));
  }
}
