package com.lujunyu.jvm;

/**
 * VM args: -Xss128k
 *
 * @author jerry
 *     <p>测试HotSpot的虚拟机栈和本地方法栈的内存溢出。
 */
public class JavaVMStackSOF {
  private int stackLength = 1;

  public void stackLeak() {
    stackLength++;
    stackLeak();
  }

  public static void main(String[] args) {
    JavaVMStackSOF oom = new JavaVMStackSOF();
    try {
      oom.stackLeak();
    } catch (Throwable e) {
      System.out.println("stack length" + oom.stackLength);
    }
  }
}
