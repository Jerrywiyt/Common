package com.lujunyu.proxy;

public class OperateImpl implements IOperate {
    @Override
    public void print(String arg) {
        System.out.println("OperateImp >> "+arg);
    }
}
