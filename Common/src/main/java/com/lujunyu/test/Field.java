package com.lujunyu.test;

public enum Field {
  NAME("姓名"),
  GENDER("性别");

  Field(String desc) {
    this.desc = desc;
  }

  private String desc;
}
