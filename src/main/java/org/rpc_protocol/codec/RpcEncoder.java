package org.rpc_protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.rpc_protocol.protocol.Header;
import org.rpc_protocol.protocol.RpcProtocol;
import org.rpc_protocol.serial.Serializer;
import org.rpc_protocol.serial.SerializerManager;

public class RpcEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        RpcProtocol<Object> msg = (RpcProtocol<Object>) o;
        Header header = msg.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getSerialType());
        byteBuf.writeByte(header.getReqType());
        byteBuf.writeLong(header.getRequestId());
        Serializer serializer = SerializerManager.getSerializer(header.getSerialType());
        byte[] data = serializer.serialize(msg.getContent());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
        System.out.println("RpcEncoder has encoded");
    }
}
