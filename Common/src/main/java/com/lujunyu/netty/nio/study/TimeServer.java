package com.lujunyu.netty.nio.study;

public class TimeServer {
	private static final int PORT = 9090;
	public static void main(String args[]){
		MultiplexerTimeServer server = new MultiplexerTimeServer(PORT);
		new Thread(server).start();
	}
}

