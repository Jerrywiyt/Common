package com.lujunyu.jdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFile {
  public static void main(String[] args) throws IOException {
    File tempFile = File.createTempFile("test-", "1");
    try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
      outputStream.write("111".getBytes());
    }

    System.out.println(tempFile.getAbsolutePath());

    System.out.println(File.createTempFile("test-", "1").getAbsolutePath());
    System.out.println(File.createTempFile("test-", "1").getAbsolutePath());
    System.out.println(File.createTempFile("test-", "1").getAbsolutePath());
    System.out.println(File.createTempFile("test-", "1").getAbsolutePath());
  }
}
