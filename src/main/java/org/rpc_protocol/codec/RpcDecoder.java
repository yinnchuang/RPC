package org.rpc_protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.rpc_protocol.protocol.Header;
import org.rpc_protocol.protocol.RpcProtocol;
import org.rpc_protocol.protocol.RpcRequest;
import org.rpc_protocol.protocol.RpcResponse;
import org.rpc_protocol.constant.ReqType;
import org.rpc_protocol.constant.RpcConstant;
import org.rpc_protocol.serial.Serializer;
import org.rpc_protocol.serial.SerializerManager;

import java.util.List;

@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < RpcConstant.HEADER_LENGTH) {
            return;
        }
        String s = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("RpcDecoder is decoding:"+s);
        byteBuf.markReaderIndex();
        short magic = byteBuf.readShort();
        if(magic != RpcConstant.MAGIC) {
            throw new IllegalArgumentException("invalid magic");
        }
        byte serialType = byteBuf.readByte();
        byte reqType = byteBuf.readByte();
        long requestId = byteBuf.readLong();
        int dataLength = byteBuf.readInt();

        if(byteBuf.readableBytes() < dataLength ) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);

        Header header = new Header(magic, serialType, reqType, requestId, dataLength);
        Serializer serializer = SerializerManager.getSerializer(serialType);
        ReqType rt = ReqType.findByteCode(reqType);

        switch (rt) {
            case REQUEST:{
                RpcRequest request = serializer.deserialize(data, RpcRequest.class);
                RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
                reqProtocol.setHeader(header);
                reqProtocol.setContent(request);
                list.add(reqProtocol);
                break;
            }
            case RESPONSE:{
                RpcResponse response = serializer.deserialize(data, RpcResponse.class);
                RpcProtocol<RpcResponse> respProtocol = new RpcProtocol<>();
                respProtocol.setHeader(header);
                respProtocol.setContent(response);
                list.add(respProtocol);
                break;
            }
            case HEARTBEAT:{
            }
            default:{
                break;
            }
        }
    }
}
