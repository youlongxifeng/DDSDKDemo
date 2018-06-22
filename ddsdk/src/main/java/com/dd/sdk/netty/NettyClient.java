package com.dd.sdk.netty;

import android.text.TextUtils;
import android.util.Log;

import com.dd.sdk.BuildConfig;

import org.json.JSONObject;

import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netty
 * @class describe
 * @time 2018/6/11 14:41
 * @change
 * @class describe netty客户端
 */


public class NettyClient {
    private final static String TAG = NettyClient.class.getSimpleName();
    /**
     * 是否连接
     */
    private boolean isConnect = false;//
    private NettyListener listener;
    private int port;//端口
    private String host;//域名
    private String mGuid;
    private SocketChannel socketChannel;
    private ChannelFuture channelFuture = null;

    public NettyClient(NettyListener nettyListener, int port, String host, String guid) {
        this.listener = nettyListener;
        this.mGuid = guid;
        this.port = port;
        this.host = host;

        this.port = 5611;//正式版要修改的
        this.host = "172.21.21.25";////正式版要修改的
        Log.i(TAG, "connect===port=" + port + "   host=" + host);
    }

    /**
     * 开始连接
     */
    public void connect() {
        EventLoopGroup eventLoopGroup;
        try {
            eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                    socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast(new NettyClientHandler(mGuid, listener));

                  /*  SslContext sslCtx = SslContextBuilder.forClient()
                                                         .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));    // 开启SSL
                  pipeline.addLast(new LoggingHandler(LogLevel.INFO));    // 开启日志，可以设置日志等级*/
                }
            });
            channelFuture = bootstrap.connect(host, port).addListener(ChannelFuture()).sync();

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "connect===e=" + e);
        } finally {

        }
    }

    /**
     * 通道连接
     *
     * @return
     */
    private ChannelFutureListener ChannelFuture() {
        ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Log.i(TAG, "channelFuture====" + channelFuture.isSuccess());
                if (channelFuture.isSuccess()) {
                    socketChannel = (SocketChannel) channelFuture.channel();
                    isConnect = true;
                } else {
                    isConnect = false;
                    connect();
                }

            }
        };
        return channelFutureListener;
    }

    /**
     * 断开重连 还需要优化
     */
    public void reconnect() {
        if (socketChannel != null && channelFuture.channel().isOpen()) {
            socketChannel.close();
        }
        connect();
    }

    public void close() {
        if (socketChannel != null && socketChannel.isOpen()) {
            socketChannel.close();
        }
    }

    /**
     * @param listener
     */
    public void setListener(NettyListener listener) {
        this.listener = listener;
    }


    public boolean sendMsgToServer(String data, ChannelFutureListener listener) {
        boolean flag = socketChannel != null && isConnect;
        if (flag) {
//			ByteBuf buf = Unpooled.copiedBuffer(data);
//            ByteBuf byteBuf = Unpooled.copiedBuffer(data + System.getProperty("line.separator"), //2
//                    CharsetUtil.UTF_8);
            socketChannel.writeAndFlush(data + System.getProperty("line.separator")).addListener(listener);
        }
        return flag;
    }

    public boolean sendMsgToServer(byte[] data, ChannelFutureListener listener) {
        boolean flag = socketChannel != null && isConnect;
        if (flag) {
            //ByteBuf buf = Unpooled.copiedBuffer(data);
            String str = new String(data) + "*" + System.getProperty("line.separator");
            ByteBuf buf = Unpooled.copiedBuffer(str.getBytes());
            socketChannel.writeAndFlush(buf).addListener(listener);
        }
        return flag;
    }

    protected void sendReponse(String cmd, String content, boolean success) {

    }

    public static String builder(String cmd, String content, String sn, String guid, boolean success) {
        JSONObject o = new JSONObject();
        try {
            o.put("cmd", cmd);
            o.put("success", success);
            o.put("guid", guid);
            if (!TextUtils.isEmpty(content)) {
                o.put("message", content);
            }
            if (!TextUtils.isEmpty(sn)) {
                o.put("sn", sn);
            }
            o.put("version", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o.toString();
    }
}
