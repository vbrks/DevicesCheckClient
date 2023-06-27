package org.example.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Main;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private PropertiesHandler propertiesHandler = new PropertiesHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("config");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) {
        if (message.startsWith("#config_data")) {
            propertiesHandler.setProperties(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
        cause.printStackTrace();
        ctx.close();
        Main.startEventListener(Main.startClient());
    }
}
