package org.geekswrod.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class Id implements Serializable {
    private static final long serialVersionUID = 7841179129755355916L;

    /**
     * 机器id
     */
    private long machineId;

    /**
     * 类型
     */
    private long type;

    /**
     * 版本
     */
    private long version;

    /**
     * 生成方法
     */
    private long genMethod;

    private long seq;

    private long time;


}
