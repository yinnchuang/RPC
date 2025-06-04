package org.rpc_protocol.serial;

import com.alibaba.fastjson.JSON;
import org.rpc_protocol.constant.SerialType;

public class JsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String jsonStr = new String(data);
        return JSON.parseObject(jsonStr, clazz);
    }

    @Override
    public byte getType() {
        return SerialType.JSON_SERIAL.getCode();
    }
}
