package org.rpc_protocol.constant;

public enum ReqType {
    REQUEST((byte) 1),
    RESPONSE((byte) 2),
    HEARTBEAT((byte) 3);

    private final byte code;

    ReqType(byte b) {
        this.code = b;
    }

    public byte getCode(){
        return this.code;
    }

    public static ReqType findByteCode(int code){
        for(ReqType reqType : ReqType.values()){
            if(reqType.getCode() == code){
                return reqType;
            }
        }
        return null;
    }
}
