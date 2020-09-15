package com.lujunyu.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Test3 {
  public static void main(String[] args) throws IOException {
    Map<String, Set<String>> parse = parse("/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_138183.txt");

    int cnt = 0;

    for(Map.Entry<String,Set<String>> entry: parse.entrySet()){
      if(entry.getValue().size()>0){
        System.out.println(entry.getKey());
        cnt++;
      }
    }

    System.out.println(cnt);


  }

  private static void diff() throws IOException {
    Map<String, Set<String>> map =
        parse(
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_137923.txt");

    List<String> all = read("/Users/jerry_lu/Downloads/bejing-08-softblackout-listings.csv");

    List<String> need = Lists.newArrayList();
    for (String line : all) {
      String[] split = line.split(",");
      if (map.containsKey(split[0]) && map.get(split[0]).size() > 0) {
        System.out.println(line);
      } else {
        need.add(line);
      }
    }
    write(need, "/Users/jerry_lu/Downloads/2020-08-28-bejing-blackout.csv");
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

  private static void print(Map<String, Set<String>> maps) {
    try {
      List<String> listings = new ArrayList<>();
      for (Map.Entry<String, Set<String>> entry : maps.entrySet()) {
        if (entry.getValue().size() > 0) {
          listings.add(entry.getKey());
        }
      }

      Collections.sort(listings);

      File file = new File("/Users/jerry_lu/Downloads/2020-8-27.txt");
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
      for (String line : listings) {
        bufferedWriter.write(line);
        bufferedWriter.write("\r\n");
      }
      bufferedWriter.flush();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static Map<String, Set<String>> parse(String fileName) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
      String line;
      Map<String, Set<String>> map = Maps.newHashMap();
      while ((line = bufferedReader.readLine()) != null) {
        if (line.startsWith("disalbe_dates_for")) {
          String[] tags = line.split(" ");
          String listing_id = tags[1];
          List<String> dates = new ArrayList<>();
          if (tags.length > 2) {
            dates = Arrays.asList(tags).subList(2, tags.length);
          }
          map.put(listing_id, Sets.newHashSet(dates));
        }
      }
      return map;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
