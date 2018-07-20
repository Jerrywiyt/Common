package com.lujunyu.netty.basic.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Study1 {

	public static void main(String args[]) throws Exception{
//		test1();
		test2();
//		test3();
	}
	/**
	 * @throws Exception 
	 */
	private static void test3() throws Exception {
		RandomAccessFile afile = new RandomAccessFile(Thread.currentThread().getContextClassLoader().getResource("com/lujunyu/netty/basic/nio/nio.test").getFile(), "rw");
		FileChannel fileChannel = afile.getChannel();
		ByteBuffer buff1 = ByteBuffer.allocate(10);
		ByteBuffer buff2 = ByteBuffer.allocate(10);
		ByteBuffer buff3 = ByteBuffer.allocate(10000);
		ByteBuffer[] buffs = new ByteBuffer[]{buff1,buff2,buff3};
		fileChannel.read(buffs);
		buff1.flip();
		buff2.flip();
		buff3.flip();
		while(buff1.hasRemaining()){
			System.out.print((char)buff1.get());
		}
		System.out.println();
		while(buff2.hasRemaining()){
			System.out.print((char)buff2.get());
		}
		System.out.println();
		while(buff3.hasRemaining()){
			System.out.print((char)buff3.get());
		}
		buff1.clear();
		buff2.clear();
		buff3.clear();
		buff1.put((byte)115);
		buff1.put((byte)115);
		buff1.put((byte)115);
		buff1.put((byte)115);
		buff1.put((byte)115);
		buff1.put((byte)115);
		buff1.put((byte)115);
		buff1.put((byte)115);
		buff2.put((byte)116);
		buff2.put((byte)116);
		buff2.put((byte)116);
		buff2.put((byte)116);
		buff2.put((byte)116);
		buff2.put((byte)116);
		buff2.put((byte)116);
		buff2.put((byte)116);
		buff3.put((byte)117);
		buff3.put((byte)117);
		buff1.flip();
		buff2.flip();
		buff3.flip();
		fileChannel.write(buffs);
		fileChannel.close();
		afile.close();
	}

	/**
	 */
	private static void test2() {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		byte[] b = new byte[]{1,2};
		buffer.put(b);
		System.out.println(buffer);
		buffer.flip();
		System.out.println(buffer.get());
		System.out.println(buffer);
		buffer.compact();
		System.out.println(buffer);
		buffer.mark();
		buffer.reset();
		buffer.rewind();
		buffer.clear();
	}

	private static void test1() throws IOException {
		RandomAccessFile afile = new RandomAccessFile(Thread.currentThread().getContextClassLoader().getResource("com/lujunyu/netty/basic/nio/nio.test").getFile(), "rw");
		FileChannel fileChannel = afile.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		int len = fileChannel.read(byteBuffer);
		while(len!=-1){
			System.out.println("received : "+len);
			byteBuffer.flip();
			while(byteBuffer.hasRemaining()){
				System.out.print((char)byteBuffer.get());
			}
			System.out.println();
			byteBuffer.clear();
			len = fileChannel.read(byteBuffer);
		}
		afile.close();
	}
}
