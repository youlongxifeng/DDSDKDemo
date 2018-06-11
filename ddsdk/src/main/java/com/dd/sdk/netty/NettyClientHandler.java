package com.dd.sdk.netty;

import android.util.Base64;
import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netty
 * @class describe
 * @time 2018/6/11 14:41
 * @change
 * @class describe
 */

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    private NettyListener listener;

    public NettyClientHandler(NettyListener listener) {
        this.listener = listener;
    }

    /**
     * 利用写空闲发送心跳检测消息
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            Log.i("EEE", "userEventTriggered=" + e.state());
            switch (e.state()) {
                case WRITER_IDLE:
                    final String msg = String.format(
                            "{\"guid\":\"%s\",\"cmd\":\"heart_beat\",\"version\":\"%s\"}", "DDD4001708-05946", "5.8.501.0");
                    byte[] data = Base64.encode((msg).getBytes(), Base64.DEFAULT);
                    String str = new String(data) + "*" + System.getProperty("line.separator");
                    ctx.writeAndFlush(str);
                    Log.i("EEE", "send ping to server----------");
                    break;
                case READER_IDLE:
                    break;
                case ALL_IDLE:
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 接收来自服务器的信息
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        byte[] b = new byte[1024 * 7];
        byte[] data = Base64.decode(s.getBytes(), Base64.DEFAULT);
        String str = new String(data);
        Log.i("EEE", "收到服务端消息：----------s=" + str);
    }

    /**
     * 异常捕获
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Log.e("EEE", "exceptionCaught  cause=" + cause.getMessage());
    }


    /**
     * 连接成功
     * 通道活动
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e("EEE", "channelActive  连接成功");
        //        NettyClient.getInstance().setConnectStatus(true);

    }

    /**
     * 通道不活动
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.e("EEE", "channelInactive  出现问题需要重连ctx=" + ctx.name());
        //        NettyClient.getInstance().setConnectStatus(false);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_CLOSED);
        //   NettyClient.getInstance().reconnect();
    }
}
