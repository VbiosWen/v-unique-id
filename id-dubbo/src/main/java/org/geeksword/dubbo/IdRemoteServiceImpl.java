package org.geeksword.dubbo;

import org.geekswrod.api.Id;
import org.geekswrod.api.IdService;

import java.time.LocalDateTime;

public class IdRemoteServiceImpl implements IdService {


    @Override
    public long genId() {
        return 0;
    }

    @Override
    public Id expId(long id) {
        return null;
    }

    @Override
    public long makeId(long time, long seq) {
        return 0;
    }

    @Override
    public long makeId(long time, long seq, long genMethod) {
        return 0;
    }

    @Override
    public long makeId(long time, long seq, long genMethod, long machine) {
        return 0;
    }

    @Override
    public long makeId(long time, long seq, long genMethod, long machine, long type) {
        return 0;
    }

    @Override
    public long makeId(long time, long seq, long genMethod, long machine, long type, long version) {
        return 0;
    }

    @Override
    public LocalDateTime transTime(long time) {
        return null;
    }
}
