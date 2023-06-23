package org.example.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private PropertiesHandler propertiesHandler = new PropertiesHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Connected " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) {
        System.out.println("Received message from server: " + message);
        if (message.startsWith("#config_data")) {
            propertiesHandler.setProperties(message);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        System.out.println("Disconnected " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
