package org.rpc_protocol.serial;

public interface Serializer {
    <T> byte[] serialize(T obj);
    <T> T deserialize(byte[] data, Class<T> clazz);
    byte getType();
}
