package org.rpc_protocol.Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.rpc_protocol.constant.ReqType;
import org.rpc_protocol.constant.RpcConstant;
import org.rpc_protocol.protocol.Header;
import org.rpc_protocol.protocol.RpcProtocol;
import org.rpc_protocol.protocol.RpcRequest;
import org.rpc_protocol.protocol.RpcResponse;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcProtocol<RpcRequest> msgConvert = (RpcProtocol<RpcRequest>) msg;
        Header header = msgConvert.getHeader();
        RpcRequest request = msgConvert.getContent();
        // 通过反射调用
        Class<?> clazz = Class.forName(request.getClassName());
        // 用Spring获取
        Object object = SpringBeanManager.getBean(clazz);
        Method method= clazz.getMethod(request.getMethodName(), request.getParameterTypes());
        Object res = method.invoke(object, request.getParams());

        RpcResponse response = new RpcResponse();
        response.setData(res);
        response.setMsg("success");
        RpcProtocol<RpcResponse> protocol = new RpcProtocol<>();

        header.setReqType(ReqType.RESPONSE.getCode());
        protocol.setHeader(header);
        protocol.setContent(response);

        ctx.writeAndFlush(protocol);
    }
}
