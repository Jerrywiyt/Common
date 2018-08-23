package com.lujunyu.netty.src;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class TestByteBuf {
    public static void main(String args[]) throws UnsupportedEncodingException {
        ByteBuf byteBuf = Unpooled.copyInt(10);
        byteBuf.writeInt(1);

        ByteBuf b = Unpooled.buffer(1,10);
        b.writeInt(1);
        b.writeBytes("一个大傻子".getBytes("utf-8"));

        ByteBuffer nioByteBuffer = byteBuf.nioBuffer();
    }
}
