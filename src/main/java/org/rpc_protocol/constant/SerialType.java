package org.rpc_protocol.constant;

public enum SerialType {
    JSON_SERIAL((byte) 0),
    JAVA_SERIAL((byte) 1);

    private final byte code;

    SerialType(byte b) {
        this.code = b;
    }

    public byte getCode() {
        return code;
    }
}
