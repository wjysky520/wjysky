package com.wjysky.config.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

/**
 * @ClassName : NewhowDevServerHandler
 * @Description : Netty服务处理类
 * @company : wjysky
 * @Author : 王俊元（wangjunyuan@newhow.com.cn）
 * @Date: 2020-07-02 10:47
 */
@Sharable
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static NettyServerHandler serverHandler = new NettyServerHandler();

    private NettyServerHandler() {}

    public static NettyServerHandler getInstance() {
        return serverHandler;
    }

    private NettyServer nettyServer;

    public void setServer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    //客户端下线或者强制退出
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if(null != nettyServer) {
            nettyServer.NotifyConnectStatus(false, ctx);
        }
    }

    //有客户端连接的通知
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        if(null != nettyServer) {
            nettyServer.NotifyConnectStatus(true, ctx);
        }
    }

    //向客户端发送消息
    public boolean writeCommand(ChannelHandlerContext ctx, byte[] data) {
        try {
//			ctx.channel().writeAndFlush(data);
            ctx.writeAndFlush(data); // 两者区别参考：https://blog.csdn.net/FishSeeker/article/details/78447684
            log.debug("Netty服务器向客户端发送信息成功");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @ClassName FenceInBoundHandler
     * @Title userEventTriggered
     * @Description 当连接的空闲时间（读或者写）太长时，将会触发一个 IdleStateEvent 事件
     * @param ctx
     * @param evt
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2020-03-30 11:00
     * @return void
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            if (idleStateEvent.state().equals(IdleState.READER_IDLE)){ // 长期未收到客户器发送的信息
                log.info("Netty服务器已经30秒没有收到客户端的信息！");
                // 向客户端发送消息
//                ctx.channel().writeAndFlush("hi\r\n".getBytes(CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else if (idleStateEvent.state().equals(IdleState.WRITER_IDLE)) { // 长期没有发送数据至客户端
                log.info("Netty服务器已经30秒没有发送信息给客户端！");
//                ctx.channel().writeAndFlush("hello\r\n".getBytes(CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else if (idleStateEvent.state().equals(IdleState.ALL_IDLE)) { // 长期没有读写数据至客户端

            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        if (byteBuf != null) {
            try {
                String content = byteBuf.toString(CharsetUtil.UTF_8);
                if (nettyServer != null && StringUtils.isNotBlank(content)) {
                    nettyServer.receiveMessage(ctx, content);
                }
            } catch (Exception e) {
                log.error(String.format("解析客户端[%s]上报的信息时异常", getIPString(ctx)), e);
            }
        }
    }

    private String getIPString(ChannelHandlerContext ctx) {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        int clientPort = ipSocket.getPort();
        return clientIp + ":" + clientPort;
    }
}
