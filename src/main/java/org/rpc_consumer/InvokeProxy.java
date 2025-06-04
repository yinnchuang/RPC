package org.rpc_consumer;

import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import org.rpc_protocol.Client.Client;
import org.rpc_protocol.RequestHolder;
import org.rpc_protocol.constant.ReqType;
import org.rpc_protocol.constant.RpcConstant;
import org.rpc_protocol.constant.SerialType;
import org.rpc_protocol.protocol.Header;
import org.rpc_protocol.protocol.RpcProtocol;
import org.rpc_protocol.protocol.RpcRequest;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class InvokeProxy implements InvocationHandler {
    private String address;
    private int port;
    public InvokeProxy(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcProtocol<RpcRequest> protocol = new RpcProtocol<>();
        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        Header header = new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.getCode(), ReqType.REQUEST.getCode(), requestId, 0);

        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);

        protocol.setHeader(header);
        protocol.setContent(request);

        Client client = new Client(address, port);
        Promise<Object> promise = new DefaultPromise<>(new DefaultEventLoop());
        RequestHolder.REQUEST_MAP.put(requestId, promise);
        client.sendRequest(protocol);

        promise.await();
        return promise.get();
    }
}
