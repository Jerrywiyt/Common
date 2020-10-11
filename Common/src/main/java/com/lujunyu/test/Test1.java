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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Test1 {
  public static void main(String args[]) throws IOException {
    parse(
        Lists.newArrayList(
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_141365.txt",
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_141368.txt",
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_141374.txt",
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_141503.txt",
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_141373.txt"),
        Lists.newArrayList(
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_141933.txt",
            "/Users/jerry_lu/Downloads/one_off_china_compliance_blackout_disable_calendar_on_demand.rb_runs_141938.txt"));

    difference(
        Lists.newArrayList("/Users/jerry_lu/Downloads/2020-9-25.txt"),
        Lists.newArrayList("/Users/jerry_lu/Downloads/2020-9-28.txt"));
  }

  public static void difference(List<String> before, List<String> after) throws IOException {
    Set<String> befores = read(before);
    Set<String> afters = read(after);

    Set<String> result = Sets.newHashSet();
    for (String line : afters) {
      if (!befores.contains(line)) {
        result.add(line);
      }
    }

    List<String> lines = Lists.newArrayList(result);

    Collections.sort(lines);

    File file = new File("/Users/jerry_lu/Downloads/diff-9-28.txt");
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
    for (String line : lines) {
      bufferedWriter.write(line);
      bufferedWriter.write("\r\n");
    }
    bufferedWriter.flush();
  }

  private static Set<String> read(List<String> files) throws IOException {
    Set<String> set = Sets.newHashSet();
    for (String file : files) {
      BufferedReader br = new BufferedReader(new FileReader(new File(file)));
      String line;
      while ((line = br.readLine()) != null) {
        set.add(line);
      }
    }
    return set;
  }

  public static void parse(List<String> before, List<String> after) {
    List<Map<String, Set<String>>> beforeL =
        before.stream().map(Test1::parse).collect(Collectors.toList());
    List<Map<String, Set<String>>> afterL =
        after.stream().map(Test1::parse).collect(Collectors.toList());

    // 相同结果做并集
    Map<String, Set<String>> beforeM = union(beforeL);
    Map<String, Set<String>> afterM = union(afterL);

    // 交集。
    Map<String, Set<String>> intersection = intersection(beforeM, afterM);

    System.out.println("屏蔽总量：" + beforeM.size());
    System.out.println("屏蔽成功总量：" + beforeM.values().stream().filter(s -> s.size() > 0).count());
    System.out.println("总打开量：" + afterM.values().stream().filter(s -> s.size() > 0).count());
    System.out.println("屏蔽后打开：" + intersection.values().stream().filter(s -> s.size() > 0).count());

    System.out.println(
        afterM.entrySet().stream()
            .filter(s -> s.getValue().size() > 0)
            .collect(Collectors.toList()));
    print(intersection);
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

      File file = new File("/Users/jerry_lu/Downloads/2020-9-28.txt");
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

  private static Map<String, Set<String>> intersection(
      Map<String, Set<String>> beforeM, Map<String, Set<String>> afterM) {
    Map<String, Set<String>> resultMap = Maps.newHashMap();
    beforeM.forEach(
        (key, value) -> {
          if (afterM.containsKey(key)) {
            resultMap.put(key, Sets.intersection(value, afterM.get(key)));
          }
        });
    return resultMap;
  }

  private static Map<String, Set<String>> union(List<Map<String, Set<String>>> lists) {
    Map<String, Set<String>> map = lists.get(0);
    for (int i = 1; i < lists.size(); i++) {
      Map<String, Set<String>> map1 = lists.get(i);
      map1.forEach(
          (key, value) -> {
            if (map.containsKey(key)) {
              map.get(key).addAll(value);
            } else {
              map.put(key, value);
            }
          });
    }
    return map;
  }

  public static Map<String, Set<String>> parse(String fileName) {
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
