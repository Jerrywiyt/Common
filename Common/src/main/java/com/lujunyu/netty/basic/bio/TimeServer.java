package com.lujunyu.netty.basic.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServer {

  private static final int PORT = 9090;

  private static Executor executor =
      new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2));

  public static void main(String args[]) throws IOException {
    startServer();
  }

  private static void startServer() throws IOException {
    ServerSocket server = null;
    Socket s = null;
    try {
      server = new ServerSocket(PORT);
      System.out.println(">>TIME SERVER START UP IN PORT:" + PORT);
      while (true) {
        s = server.accept();
        InetAddress address = s.getInetAddress();
        System.out.println(address.getHostAddress());
        //				new Thread(new TimeServerHandler(s)).start();
        executor.execute(new TimeServerHandler(s));
      }
    } finally {
      if (server != null) {
        System.out.println(">>TIME SERVER STOP..");
      }
    }
  }

  private static class TimeServerHandler implements Runnable {
    private Socket socket;

    public TimeServerHandler(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      BufferedReader br = null;
      PrintWriter pw = null;
      try {
        br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        pw = new PrintWriter(this.socket.getOutputStream(), true);
        while (true) {
          String command = br.readLine();
          System.out.println("RECEIVE COMMAND :" + command);
          if (null == command) {
            break;
          }
          pw.println(new Date().toString());
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (br != null) {
          try {
            br.close();
            br = null;
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        if (pw != null) {
          pw.close();
          pw = null;
        }
        if (socket != null) {
          try {
            this.socket.close();
            this.socket = null;
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
