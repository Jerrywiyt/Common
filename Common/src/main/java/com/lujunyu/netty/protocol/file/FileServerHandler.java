package com.lujunyu.netty.protocol.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {
    private static Logger logger = LoggerFactory.getLogger(FileServerHandler.class);
    private static final String CR = System.getProperty("line.separator");
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("ACCEPT："+msg);
        File file = new File(msg);
        if(!file.exists()){
            ctx.writeAndFlush("file not exists"+CR);
            return;
        }

        if(file.isFile()){
            ctx.writeAndFlush("Not a file"+CR);
            return;
        }

        ctx.writeAndFlush("filesize="+file.length()+CR);

        RandomAccessFile raf = new RandomAccessFile(file,"r");
        FileRegion fileRegion = new DefaultFileRegion(raf.getChannel(),0,raf.length());
        ctx.write(fileRegion);
        ctx.writeAndFlush(CR);
        raf.close();
    }
}
