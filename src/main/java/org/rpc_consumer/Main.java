package org.rpc_consumer;

import org.rpc_protocol.protocol.RpcResponse;
import org.rpc_provider.MyService;

public class Main {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy();
        MyService proxyInstance = clientProxy.clientProxy(MyService.class, "127.0.0.1", 8088);
        String result = proxyInstance.sayHello("hello");
        System.out.println("Result: " + result);
    }
}