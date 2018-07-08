package com.lujunyu.utils.excel;

import com.lujunyu.utils.excel.Excel;

/**
 * @author 58
 * @create 2018-05-07 16:05
 **/
public class User {
    @Excel(name="姓名")
    private String name;
    @Excel(name="性别")
    private String sex;
    @Excel(name="年龄")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
