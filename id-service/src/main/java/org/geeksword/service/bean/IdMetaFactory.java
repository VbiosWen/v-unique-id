package org.geeksword.service.bean;

public class IdMetaFactory {

    private static IdMeta maxPeek = new IdMeta((byte) 10, (byte) 20, (byte) 30, (byte) 2, (byte) 1, (byte) 1);

    private static IdMeta minGranularity = new IdMeta((byte) 10, (byte) 10, (byte) 40, (byte) 2, (byte) 1, (byte) 1);

    public static IdMeta getIdMeta(IdType idType){
        if(IdType.MAX_PEEK == idType){
            return maxPeek;
        }else if(IdType.MIN_GRANULARITY == idType){
            return minGranularity;
        }
        return maxPeek;
    }
}