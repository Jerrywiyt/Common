package com.lujunyu.serialize;

import java.io.Serializable;
import java.util.*;

public class Person implements Serializable {
  private String name;
  private int height;
  private boolean isMan;
  private Map<String, String> extend = new HashMap<>();
  private Set<String> set = new HashSet<>();
  private List<String> list = new ArrayList();

  private int[] arr;

  public int[] getArr() {
    return arr;
  }

  public void setArr(int[] arr) {
    this.arr = arr;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isMan() {
    return isMan;
  }

  public void setMan(boolean man) {
    isMan = man;
  }

  public Map<String, String> getExtend() {
    return extend;
  }

  public void setExtend(Map<String, String> extend) {
    this.extend = extend;
  }

  public Set<String> getSet() {
    return set;
  }

  public void setSet(Set<String> set) {
    this.set = set;
  }

  public List<String> getList() {
    return list;
  }

  public void setList(List<String> list) {
    this.list = list;
  }
}
