package com.lujunyu.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Test2 {

  public static void main(String[] args) throws IOException {
    List<String> all = read("/Users/jerry_lu/Downloads/nanjing_survey.csv");
    List<String> has = read("/Users/jerry_lu/Downloads/aa.csv");

    HashSet<String> set = Sets.newHashSet(all);
    System.out.println(set.size());

  }

  private static void write(List<String> lines, String file) throws IOException {
    BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file)));
    lines.forEach(
        line -> {
          try {
            bw.write(line);
            bw.write("\r\n");
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    bw.flush();
    bw.close();
  }

  private static List<String> read(String file) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
    String line = null;
    List<String> res = new ArrayList<>();
    while ((line = bufferedReader.readLine()) != null) {
      res.add(line);
    }
    return res;
  }
}
