package com.lujunyu.serialize;

import java.io.Serializable;

public class Person1 implements Serializable {
  private String firstName;
  private int height;
  private boolean isMan;

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public boolean isMan() {
    return isMan;
  }

  public void setMan(boolean man) {
    isMan = man;
  }
}
