package com.lujunyu.test;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test2 {

  public static void main(String[] args) throws IOException {
    List<String> all =
        read("/Users/jerry_lu/Downloads/sqllab_untitled_query_3_20200914T034411.csv");
    List<String> all1 = read("/Users/jerry_lu/Downloads/listing_id.csv");

    List<String> has = Lists.newArrayList();

    for (String line : all) {
      if (all1.contains(line.split(",")[1])) {
        has.add(line);
      }
    }

    write(has, "/Users/jerry_lu/Downloads/tttttt.csv");
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
