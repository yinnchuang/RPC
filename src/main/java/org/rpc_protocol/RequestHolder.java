package org.rpc_protocol;

import io.netty.util.concurrent.Promise;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RequestHolder {
    public static final AtomicLong REQUEST_ID = new AtomicLong(0);
    public static final ConcurrentHashMap<Long, Promise<Object>> REQUEST_MAP = new ConcurrentHashMap<>();
}
