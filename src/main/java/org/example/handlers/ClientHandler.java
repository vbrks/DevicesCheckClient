package org.example.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Main;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("config");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Main.startEventListener(Main.startClient());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws InterruptedException {
        if (message.startsWith("#config_data")) {
            PropertiesHandler.setProperties(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
        cause.printStackTrace();
        Main.startEventListener(Main.startClient());
        ctx.fireChannelUnregistered();
        ctx.channel().close();
        ctx.close();
    }
}
