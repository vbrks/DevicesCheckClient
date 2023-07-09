package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.Main;
import org.example.handlers.PropertiesHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private Bootstrap bootstrap = new Bootstrap();
    private SocketAddress address;
    private Channel channel;
    private Timer timer;

    public Client(String host, int port, Timer timer) {
        this(new InetSocketAddress(host, port), timer);
    }

    public Client(SocketAddress address, Timer timer) {
        this.address = address;
        this.timer = timer;
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(createMessageHandler());
            }
        });
        scheduleConnect(10);
    }

    public void sendMsg(String msg) {
        if (channel != null && channel.isActive()) {
            ByteBuf buf = channel.alloc().buffer().writeBytes(msg.getBytes());
            channel.writeAndFlush(buf);
        }
    }

    private void doConnect() {
        try {
            ChannelFuture f = bootstrap.connect(address);
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        future.channel().close();
                        bootstrap.connect(address).addListener(this);
                    } else {
                        channel = future.channel();
                        addCloseDetectListener(channel);
                        connectionEstablished();
                    }
                }

                private void addCloseDetectListener(Channel channel) {
                    channel.closeFuture().addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future)
                                throws Exception {
                            connectionLost();
                            scheduleConnect(5);
                        }
                    });

                }
            });
        } catch (Exception ex) {
            scheduleConnect(1000);
        }
    }

    private void scheduleConnect(long millis) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                doConnect();
            }
        }, millis);
    }

    private ChannelHandler createMessageHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                ByteBuf buf = (ByteBuf) msg;
                int n = buf.readableBytes();
                if (n > 0) {
                    byte[] b = new byte[n];
                    buf.readBytes(b);
                    handleMessage(new String(b));
                }
            }
        };
    }

    public void handleMessage(String msg) {
        if (msg.startsWith("#config_data")) {
            PropertiesHandler.setProperties(msg);
            System.out.println("Config created");

            Thread t = new Thread(() -> {
                try {
                    Main.startEventListener(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }

    public void connectionLost() {
        System.out.println("connectionLost()");
    }

    public void connectionEstablished() {
        sendMsg("config");
    }
}