package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;
import org.example.handlers.ClientHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 4242;

    private volatile Channel channel;
    public Client() {
        new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws ExceptionInInitializerError {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new StringDecoder());
                                pipeline.addLast(new StringEncoder());
                                pipeline.addLast(new ClientHandler());
                            }
                        });


                channel = bootstrap.connect(HOST, PORT).sync().channel();
                ChannelFuture channelFuture = channel.closeFuture().sync();
                channelFuture.channel().closeFuture().sync();
                channelFuture.channel().close().sync();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                group.shutdownGracefully();
            }

        }).start();

    }

    public void sendMsg(String msg){
        System.out.println("я в sendMsg " + msg);
        channel.writeAndFlush(msg).syncUninterruptibly();
    }
}
