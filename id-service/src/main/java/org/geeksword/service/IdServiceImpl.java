package org.geeksword.service;

import lombok.extern.slf4j.Slf4j;
import org.geeksword.service.bean.IdType;
import org.geekswrod.api.Id;
import org.geekswrod.api.IdService;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class IdServiceImpl extends AbstractIdServiceImpl implements IdService {

    private long sequence;

    private long lastTimestamp;

    private Lock lock = new ReentrantLock();


    public IdServiceImpl() {
    }

    public IdServiceImpl(IdType idType) {
        super(idType);
    }

    public IdServiceImpl(String idType) {
        super(idType);
    }

    @Override
    protected void populateId(Id id) {
        Lock lock = this.lock;
        lock.lock();
        try {
            long timestamp = this.genTime();
            validateTimestamp(lastTimestamp, timestamp);

            if (timestamp == lastTimestamp) {
                sequence++;
                sequence &= idMeta.getSeqBitsMask();
                if (sequence == 0) {
                    timestamp = this.tillNextTimeUnit(lastTimestamp);
                    // 切记，这个地方一定要赋值，不然lastTimestamp不会更新
                    lastTimestamp = timestamp;
                }
            } else {
                lastTimestamp = timestamp;
                sequence = 0;
            }

            id.setSeq(sequence);
            id.setTime(timestamp);

        } finally {
            lock.unlock();
        }
    }

    @Override
    protected long tillNextTimeUnit(long lastTimestamp) {
        if (log.isInfoEnabled()) {
            log.info(String
                             .format("Ids are used out during %d in machine %d. Waiting till next %s.",
                                     lastTimestamp, machineId,
                                     idType == IdType.MAX_PEEK ? "second" : "millisecond"));
        }
        long timestamp = this.genTime();
        while (timestamp <= lastTimestamp) {
            timestamp = this.genTime();
        }
        if (log.isInfoEnabled())
            log.info(String.format("Next %s %d is up.",
                    idType == IdType.MAX_PEEK ? "second" : "millisecond",
                    timestamp));
        return timestamp;
    }

    private void validateTimestamp(long lastTimestamp, long timestamp) {
        if (timestamp < lastTimestamp) {
            if (log.isErrorEnabled()) {
                log.error(String.format("Clock moved backwards.  Refusing to generate id for %d %s.",
                        lastTimestamp - timestamp,
                        idType == IdType.MAX_PEEK ? "second"
                                : "millisecond"));
                throw new IllegalStateException(String
                                                        .format("Clock moved backwards.  Refusing to generate id for %d %s.",
                                                                lastTimestamp - timestamp,
                                                                idType == IdType.MAX_PEEK ? "second"
                                                                        : "millisecond"));

            }
        }
    }
}
