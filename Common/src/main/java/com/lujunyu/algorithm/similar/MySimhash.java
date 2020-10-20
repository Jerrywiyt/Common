package com.lujunyu.algorithm.similar;

import java.math.BigInteger;
import java.util.*;

/** @Author wzj @Create time: 2018/12/20 17:19 @Description: */
public class MySimhash {
  /** 分词后的hash数 */
  private static final int hashbits = 64;

  private static final int groups = 4;
  private static String strSimHash;

  public MySimhash() {}

  public static String getStrSimHash() {
    return strSimHash;
  }

  /**
   * 这个是对整个字符串进行hash计算
   *
   * @return
   */
  public static BigInteger simHash(Map<String, Double> words) {
    // 定义特征向量/数组
    int[] v = new int[hashbits];
    for (Map.Entry<String, Double> word : words.entrySet()) {
      // 将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
      BigInteger whash = hash(word.getKey());
      System.out.println(word + " " + whash);
      for (int i = 0; i < hashbits; i++) {
        BigInteger bitmask = new BigInteger("1").shiftLeft(i);
        // 建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
        // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
        // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
        if (whash.and(bitmask).signum() != 0) {
          // 这里是计算整个文档的所有特征的向量和
          // 这里实际使用中需要 +- 权重，而不是简单的 +1/-1，
          v[i] += word.getValue();
        } else {
          v[i] -= word.getValue();
        }
      }
    }
    BigInteger fingerprint = new BigInteger("0");
    StringBuffer simHashBuffer = new StringBuffer();
    for (int i = 0; i < hashbits; i++) {
      // 最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
      if (v[i] >= 0) {
        fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
        simHashBuffer.append("1");
      } else {
        simHashBuffer.append("0");
      }
    }
    strSimHash = simHashBuffer.toString();
    return fingerprint;
  }

  /**
   * 对单个的分词进行hash计算;
   *
   * @param word
   * @return
   */
  private static BigInteger hash(String word) {
    if (word == null || word.length() == 0) {
      return new BigInteger("0");
    } else {
      char[] sourceArray = word.toCharArray();
      BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
      BigInteger m = new BigInteger("1000003");
      BigInteger mask = new BigInteger("2").pow(hashbits).subtract(new BigInteger("1"));
      for (char item : sourceArray) {
        BigInteger temp = BigInteger.valueOf((long) item);
        x = x.multiply(m).xor(temp).and(mask);
      }
      x = x.xor(new BigInteger(String.valueOf(word.length())));
      if (x.equals(new BigInteger("-1"))) {
        x = new BigInteger("-2");
      }
      return x;
    }
  }

  /**
   * 计算海明距离,海明距离越小说明越相似;
   *
   * @return
   */
  public static int hammingDistance(BigInteger one, BigInteger two) {
    BigInteger x = one.xor(two);
    int tot = 0;
    // 统计x中二进制位数为1的个数
    // 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，对吧，然后，n&(n-1)就相当于把后面的数字清0，
    // 我们看n能做多少次这样的操作就OK了。
    while (x.signum() != 0) {
      tot += 1;
      x = x.and(x.subtract(new BigInteger("1")));
    }
    return tot;
  }

  public static List<BigInteger> subByDistance(BigInteger one) {
    // 分成几组来检查
    int numEach = hashbits / groups;
    List<BigInteger> characters = new ArrayList<>();
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < one.bitLength(); i++) {
      // 当且仅当设置了指定的位时，返回 true
      boolean sr = one.testBit(i);
      if (sr) {
        buffer.append("1");
      } else {
        buffer.append("0");
      }
      if ((i + 1) % numEach == 0) {
        // 将二进制转为BigInteger
        BigInteger eachValue = new BigInteger(buffer.toString(), 2);
        System.out.println("----" + eachValue);
        buffer.setLength(0);
        characters.add(eachValue);
      }
    }
    return characters;
  }

  public static void main(String[] args) {

    HashMap<String, Double> map1 = new LinkedHashMap<>();
    map1.put("This", 1.0);
    map1.put("is", 1.0);
    map1.put("a", 1.0);
    map1.put("test", 1.0);
    map1.put("string", 1.0);
    map1.put("for", 1.0);
    map1.put("testing", 1.0);
    HashMap<String, Double> map2 = new LinkedHashMap<>();
    map2.put("This", 1.0);
    map2.put("is", 1.0);
    map2.put("a", 1.0);
    map2.put("test", 1.0);
    map2.put("string", 1.0);
    map2.put("for", 1.0);
    map2.put("testings", 1.0);
    BigInteger i1 = MySimhash.simHash(map1);
    System.out.println(MySimhash.getStrSimHash());
    System.out.println(i1);
    BigInteger i2 = MySimhash.simHash(map2);
    System.out.println(MySimhash.getStrSimHash());
    System.out.println(i2);
    List<BigInteger> bigIntegers = MySimhash.subByDistance(i1);
    System.out.println(MySimhash.hammingDistance(i1, i2));
    System.out.println();
  }
}
