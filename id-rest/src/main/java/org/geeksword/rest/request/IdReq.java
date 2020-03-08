package org.geeksword.rest.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdReq implements Serializable {
    private static final long serialVersionUID = 5162380937742914887L;


    private long machineId;

    private long seq;

    private long type;

    private long version;
}
