package org.geeksword.service;

import lombok.extern.slf4j.Slf4j;
import org.geeksword.service.bean.IdMeta;
import org.geeksword.service.bean.IdMetaFactory;
import org.geeksword.service.bean.IdType;
import org.geeksword.service.convert.IdConvert;
import org.geeksword.service.convert.IdConvertImpl;
import org.geeksword.service.provider.MachineIdProvider;
import org.geekswrod.api.Id;
import org.geekswrod.api.IdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public abstract class AbstractIdServiceImpl implements IdService {

    /**
     * 2020-01-01 00:00:00
     */
    public static final long EPOCH = 1577808000000L;
    protected long machineId = -1;

    protected long genMethod = 0;

    protected long type = 0;

    protected long version = 0;

    @Autowired
    protected IdConvert idConvert;

    @Autowired
    protected MachineIdProvider machineIdProvider;

    protected IdType idType;
    protected IdMeta idMeta;

    public AbstractIdServiceImpl() {
    }

    public AbstractIdServiceImpl(IdType idType) {
        this.idType = idType;
    }

    public AbstractIdServiceImpl(String idType) {
        this.idType = IdType.parse(idType);
    }

    public void init() {
        this.machineId = machineIdProvider.getMachineId();

        if (machineId < 0) {
            log.error("the machine id is not configure properly so that v-id service refuse to start.");
            throw new IllegalStateException("the machine id is not configure properly so that v-id service refuse to start.");
        }

        setIdMeta(IdMetaFactory.getIdMeta(idType));
        setType(idType.value());
        setIdConvert(new IdConvertImpl(idType));
    }

    @Override
    public long genId() {
        Id id = new Id();
        populateId(id);
        id.setMachineId(machineId);
        id.setGenMethod(genMethod);
        id.setType(type);
        id.setVersion(version);
        long ret = idConvert.convertId(id);
        if (log.isTraceEnabled()) {
            log.trace("Id: {} ==> {}", id, ret);
        }
        return ret;
    }

    protected long genTime() {
        if (idType == IdType.MAX_PEEK) {
            return (System.currentTimeMillis() - EPOCH) / 1000;
        } else if (idType == IdType.MIN_GRANULARITY) {
            return (System.currentTimeMillis() - EPOCH);
        }
        return (System.currentTimeMillis() - EPOCH) / 1000;
    }

    @Override
    public LocalDateTime transTime(long time) {
        if (idType == IdType.MAX_PEEK) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(time * 1000L + EPOCH), ZoneId.systemDefault());
        } else if (idType == IdType.MIN_GRANULARITY) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(time + EPOCH), ZoneId.systemDefault());
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time * 1000L + EPOCH), ZoneId.systemDefault());
    }

    @Override
    public Id expId(long id) {
        return idConvert.convert(id);
    }

    @Override
    public long makeId(long time, long seq) {
        return 0;
    }

    @Override
    public long makeId(long genMethod, long time, long seq, long machine) {
        return 0;
    }

    @Override
    public long makeId(long type, long genMethod, long time, long seq, long machine) {
        return 0;
    }

    @Override
    public long makeId(long version, long type, long genMethod, long time, long seq, long machine) {
        return 0;
    }


    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setIdConvert(IdConvert idConvert) {
        this.idConvert = idConvert;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public void setIdMeta(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    public void setMachineIdProvider(MachineIdProvider machineIdProvider) {
        this.machineIdProvider = machineIdProvider;
    }

    protected abstract void populateId(Id id);

    protected abstract long tillNextTimeUnit(long lastTimestamp);
}
