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
import java.util.Map;
import java.util.Set;

public class Test2 {
  public static void main(String[] args) throws IOException {
    Map<String, Set<String>> before =
        Test1.parse(
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_138183.txt");

    Set<String> success = Sets.newHashSet();
    before.entrySet().stream()
        .forEach(
            stringSetEntry -> {
              if (stringSetEntry.getValue().size() > 0) {
                success.add(stringSetEntry.getKey());
              }
            });

    System.out.println(success.size());

    List<String> read =
        read("/Users/jerry_lu/Downloads/bejing-08-softblackout-listings - 九月继续屏蔽的房源.csv");
    List<String> open = read("/Users/jerry_lu/Downloads/2020-9-21.txt");

    List<String> result = Lists.newArrayList();

    result.add(read.get(0));
    for (int i = 1; i < read.size(); i++) {
      String s = read.get(i);
      if (!success.contains(s.split(",")[0])) {
        result.add(s);
      }
    }

    write(result, "/Users/jerry_lu/Downloads/0923-listing.csv");
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
