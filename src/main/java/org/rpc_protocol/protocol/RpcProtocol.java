package org.rpc_protocol.protocol;

import java.io.Serializable;

public class RpcProtocol<T> implements Serializable {
    private Header header;
    private T content;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
