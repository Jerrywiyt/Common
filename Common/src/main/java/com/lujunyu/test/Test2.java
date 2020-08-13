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
import java.util.List;
import java.util.Set;

public class Test2 {

  public static void main(String[] args) throws IOException {
    Set<String> send = Sets.newHashSet(read("/Users/jerry_lu/Downloads/send"));
    List<String> list =
        read("/Users/jerry_lu/Downloads/sqllab_untitled_query_2_20200810T051543.csv");
    List<String> res = Lists.newArrayList();
    res.add(list.get(0));
    for (String line : list) {
      String[] split = line.split(",");
      if (send.contains(split[0])) {
        res.add(line);
      }
    }
    write(res, "/Users/jerry_lu/Downloads/second.csv");
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
