package org.rpc_protocol.Client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.rpc_protocol.codec.RpcDecoder;
import org.rpc_protocol.codec.RpcEncoder;

@Slf4j
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0))
                .addLast(new LoggingHandler())
                .addLast(new RpcDecoder())
                .addLast(new RpcEncoder())
                .addLast(new ClientHandler());
    }
}
