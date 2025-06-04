package org.rpc_protocol.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Header implements Serializable {
    private short magic;
    private byte serialType;
    private byte reqType;
    private long requestId;
    private int length;
}
