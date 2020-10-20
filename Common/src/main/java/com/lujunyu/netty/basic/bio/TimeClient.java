package com.lujunyu.netty.basic.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

  public static void start() {
    Socket s = null;
    BufferedReader br = null;
    PrintWriter pw = null;
    try {
      s = new Socket("127.0.0.1", 9090);
      br = new BufferedReader(new InputStreamReader(s.getInputStream()));
      pw = new PrintWriter(s.getOutputStream(), true);
      pw.println("QUERY TIME ORDER");
      System.out.println("SEND SUCCESS");
      String resp = br.readLine();
      System.out.println("RESULT: " + resp);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (pw != null) {
        pw.close();
      }
      if (s != null) {
        try {
          s.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String args[]) {
    start();
  }
}
