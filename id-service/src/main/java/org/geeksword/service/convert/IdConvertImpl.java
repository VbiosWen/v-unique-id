package org.geeksword.service.convert;

import org.geeksword.service.bean.IdMeta;
import org.geeksword.service.bean.IdMetaFactory;
import org.geeksword.service.bean.IdType;
import org.geekswrod.api.Id;

public class IdConvertImpl implements IdConvert {

    private IdType idType;

    public IdConvertImpl() {
    }

    public IdConvertImpl(IdType idType) {
        this.idType = idType;
    }

    @Override
    public long convertId(Id id) {
        return doConvert(id, IdMetaFactory.getIdMeta(idType));
    }

    private long doConvert(Id id, IdMeta idMeta) {
        long ret = 0;

        ret |= id.getMachineId();
        ret |= id.getSeq() << idMeta.getSeqBitsStartPos();
        ret |= id.getTime() << idMeta.getTimeBitsStartPos();
        ret |= id.getGenMethod() << idMeta.getGenMethodBitsStartPos();
        ret |= id.getType() << idMeta.getTypeBitsStartPos();
        ret |= id.getVersion() << idMeta.getVersionBitsStartPos();
        return ret;
    }

    @Override
    public Id convert(long id) {
        return doConvert(id, IdMetaFactory.getIdMeta(idType));
    }

    private Id doConvert(long id, IdMeta idMeta) {
        Id ret = new Id();
        ret.setMachineId(id & idMeta.genMachineBitsMask());
        ret.setSeq(id >>> idMeta.getSeqBitsStartPos() & idMeta.getSeqBitsMask());


        return ret;
    }
}
