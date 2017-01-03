package com.lujunyu.netty.nio.study;

import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class MultiplexerTimeServer implements Runnable{
	//��·����
	private Selector selector;
	
	//serverChannel
	private ServerSocketChannel serverSocketChannel;
	
	private volatile boolean stop;
	/**
	 * @param port
	 */
	public MultiplexerTimeServer(int port) {
		try{
			selector = Selector.open();
			serverSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
			
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run() {
		
	}

}
