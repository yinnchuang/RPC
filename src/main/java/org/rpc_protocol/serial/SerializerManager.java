package org.rpc_protocol.serial;

import org.rpc_protocol.constant.SerialType;

import java.util.concurrent.ConcurrentHashMap;

public class SerializerManager {
    private final static ConcurrentHashMap<Byte, Serializer> serializers = new ConcurrentHashMap<>();
    static {
        Serializer jsonSerializer = new JsonSerializer();
        Serializer javaSerializer = new JavaSerializer();
        serializers.put(SerialType.JSON_SERIAL.getCode(), jsonSerializer);
        serializers.put(SerialType.JAVA_SERIAL.getCode(), javaSerializer);
    }
    public static Serializer getSerializer(byte key){
        return serializers.get(key);
    }
}
