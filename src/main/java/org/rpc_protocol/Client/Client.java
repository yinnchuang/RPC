package org.rpc_protocol.Client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.rpc_protocol.protocol.RpcProtocol;
import org.rpc_protocol.protocol.RpcRequest;

@Slf4j
public class Client {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private String address;
    private int port;
    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        bootstrap = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
    }
    public void sendRequest(RpcProtocol<RpcRequest> protocol) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(address, port).sync();
        future.addListener(listen->{
            if (future.isSuccess()) {
                System.out.println("client send request success");
            }else{
                System.out.println("fail");
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        future.channel().writeAndFlush(protocol);
        // future.channel().closeFuture().sync();
    }
}
