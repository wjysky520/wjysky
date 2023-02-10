package com.wjysky.facade.netty;

import io.netty.channel.ChannelHandlerContext;

public interface INettyFacade {

    void connect(ChannelHandlerContext ctx);

    void disconnect(ChannelHandlerContext ctx);

    void receive(ChannelHandlerContext ctx, String data);
}
