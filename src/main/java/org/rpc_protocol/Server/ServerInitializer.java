package org.rpc_protocol.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.rpc_protocol.codec.RpcDecoder;
import org.rpc_protocol.codec.RpcEncoder;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0))
                .addLast(new RpcDecoder())
                .addLast(new RpcEncoder())
                .addLast(new ServerHandler());
    }
}
