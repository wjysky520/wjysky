package com.wjysky.config.netty.server;

import com.wjysky.facade.netty.INettyFacade;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : NewhowDevServer
 * @Description : TODO
 * @company : 杭州远昊科技有限公司
 * @Author : 王俊元（wangjunyuan@newhow.com.cn）
 * @Date: 2020-12-16 13:39
 */
@Slf4j
@Component
public class NettyServer {

    @Resource
    private INettyFacade nettyFacade;

    /**
     * NioEventLoop并不是一个纯粹的I/O线程，它除了负责I/O的读写之外
     * 创建了两个NioEventLoopGroup，
     * 它们实际是两个独立的Reactor线程池。
     * 一个用于接收客户端的TCP连接，
     * 另一个用于处理I/O相关的读写操作，或者执行系统Task、定时任务Task等。
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    public static ChannelFuture future = null; // 防止netty服务多次被启动

    @Getter
    @Setter
    private static Map<String, ChannelHandlerContext> channelMap = new HashMap<>();

    private static boolean needReset = false;//是否需要重连

    public void myInit(String hostname, int port) {
        new Thread(()->{
            try {
                if (future != null) {
                    if (future.isSuccess()) {
                        log.info("Netty服务已启动，请勿重复启动");
                        return;
                    } else {
                        future.channel().close(); // 直接关闭
                    }
                }
                future = start(hostname, port);
                // 服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
                future.channel().closeFuture().syncUninterruptibly();
            } catch (Exception e) {
                log.info("Netty服务开启时异常", e);
            }
        }).start();
        NettyServerHandler.getInstance().setServer(this);
    }

    /**
     * 启动 刷卡设备 netty服务
     * @param hostname
     * @param port
     * @return
     * @throws Exception
     */
    public ChannelFuture start(String hostname, int port) throws Exception {
        log.info("开始启动Netty服务......");
        ChannelFuture future = null;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
            bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
            bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(512, 1024, 2048));//缓冲大小，，initial要介于minimum和maximum之间
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
                    p.addLast(new DelimiterBasedFrameDecoder(2048,true,true,delimiter));
                    // 为监听客户端read/write事件的Channel添加用户自定义的ChannelHandler
                    p.addLast(new IdleStateHandler(600, 0, 0), NettyServerHandler.getInstance());
                    p.addLast(new IdleStateHandler(600, 0, 0), NettyTestHandler.getInstance());
                }
            });
            future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                needReset = false;
                log.info("Netty服务[{}:{}]启动成功", hostname, port);
                startReConnectThread();
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(String.format("Netty服务[%s:%s]启动时异常", hostname, port), e);
            needReset = true;
        } finally {
            if (future != null && future.isSuccess()) {
                log.info("Netty服务[{}:{}]监听中......", hostname, port);
            } else {
                log.error("Netty服务[{}:{}]启动失败", hostname, port);
            }
        }
        return future;
    }

    /**
     *
     * @ClassName BrainServer
     * @Title NotifyConnectStatus
     * @Description 客户端连接或者断开通知
     * @param isConnected
     * @param ctx
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2020-07-02 15:03
     * @result void
     * @see
     **/
    public void NotifyConnectStatus(boolean isConnected, ChannelHandlerContext ctx) {
        if (isConnected) { // 连接
            nettyFacade.connect(ctx);
        } else { // 断开
            nettyFacade.disconnect(ctx);
        }
    }

    /**
     *
     * @ClassName BrainServer
     * @Title sendMessage
     * @Description 发送信息
     * @param ctx
     * @param content
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2020-07-02 16:10
     * @result void
     * @see
     **/
    public boolean sendMessage(ChannelHandlerContext ctx, String content) {
        try {
            return NettyServerHandler.getInstance().writeCommand(ctx, content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @ClassName BrainServer
     * @Title receiveMessage
     * @Description 接收信息
     * @param ctx
     * @param data
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2020-07-02 16:12
     * @result void
     * @see
     **/
    public void receiveMessage(ChannelHandlerContext ctx, String data) {
        nettyFacade.receive(ctx, data);
    }

    /**
     * @param
     * @return void
     * @ClassName QRCodeBoxServer
     * @Title destroy
     * @Description 停止服务
     **/
    public void destroy() {
        log.info("开始停止远昊设备Netty服务......");
        if (future != null && future.channel() != null) {
            future.channel().close(); // 直接关闭
            future = null;
        }
        bossGroup.shutdownGracefully();
        log.info("Netty服务停止成功");
    }

    public static void addChannel(String channelId, ChannelHandlerContext ctx) {
        channelMap.put(channelId, ctx);
    }

    public static void delChannel(String channelId) {
        channelMap.remove(channelId);
    }

    public void startReConnectThread() {
        while(true){
            if (needReset) {
                log.info("关闭Netty服务释放资源，10秒后重新连接服务器");
                restartServer();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void restartServer() {
        destroy();
        int port = 9999;
        try {
            start("0.0.0.0", port);
        } catch (Exception e) {
            log.error("Netty服务重启时异常");
        }
    }
}
