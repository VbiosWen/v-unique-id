package org.geekswrod.api;

import java.time.LocalDateTime;

public interface IdService {

    /**
     * 生成id
     * @return long 返回id
     */
    long genId();

    /**
     * 解析id，提供id的逆向解析
     * @param id 生成的唯一id
     * @return {@link Id}
     */
    Id expId(long id);

    /**
     * 用来伪造某一时间的id
     * @param time 时间
     * @param seq
     * @return
     */
    long makeId(long time,long seq);

    long makeId(long time,long seq,long genMethod);

    long makeId(long time,long seq,long genMethod,long machine);

    long makeId(long time,long seq,long genMethod,long machine,long type);

    long makeId(long time,long seq,long genMethod,long machine,long type,long version);

    /**
     * 将整型时间翻译成格式化时间
     * @param time
     * @return
     */
    LocalDateTime transTime(long time);
}
