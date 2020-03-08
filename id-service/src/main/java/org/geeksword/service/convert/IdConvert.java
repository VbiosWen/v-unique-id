package org.geeksword.service.convert;

import org.geekswrod.api.Id;

public interface IdConvert {
    /**
     * 正向生成id
     * @param id 结构化id信息
     * @return 生成唯一性id
     */
    long convertId(Id id);

    /**
     * 逆向根据id推导信息
     * @param id 生成的id
     * @return 结构化id信息
     */
    Id convert(long id);
}
