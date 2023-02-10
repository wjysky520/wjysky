package com.wjysky.facade.netty.impl;

import com.wjysky.facade.netty.INettyFacade;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettyFacadeImpl implements INettyFacade {

    @Override
    public void connect(ChannelHandlerContext ctx) {
        log.info("有客户端接入");
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx) {
        log.info("有客户端断开");
    }

    @Override
    public void receive(ChannelHandlerContext ctx, String data) {

        log.info("收到客户端的消息：{}", data);
    }
}
