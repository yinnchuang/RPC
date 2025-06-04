package org.rpc_protocol.Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Promise;
import org.rpc_protocol.RequestHolder;
import org.rpc_protocol.protocol.RpcProtocol;
import org.rpc_protocol.protocol.RpcResponse;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcProtocol<RpcResponse> rpcProtocol = (RpcProtocol<RpcResponse>) msg;
        System.out.println("Client received result");
        System.out.println(rpcProtocol.getContent());
        long RequestId = rpcProtocol.getHeader().getRequestId();
        Promise<Object> promise = RequestHolder.REQUEST_MAP.remove(RequestId);
        if (promise != null) {
            promise.setSuccess(rpcProtocol.getContent().toString());
        }
    }
}
