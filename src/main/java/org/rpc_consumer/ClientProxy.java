package org.rpc_consumer;

import org.springframework.cglib.proxy.Proxy;

public class ClientProxy {
    public <T> T clientProxy(final Class<T> interfaceCls, final String host, final int port){
        return (T) Proxy.newProxyInstance(
                interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},
                new InvokeProxy(host, port));
    }
}
