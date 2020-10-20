package com.lujunyu.netty.protocol.netty.struct;

import lombok.Data;

@Data
public class NettyMessage {
  private Header header;
  private Object body;
}
