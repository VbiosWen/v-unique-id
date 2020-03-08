package org.geeksword.service.factory;

import lombok.extern.slf4j.Slf4j;
import org.geekswrod.api.IdService;
import org.springframework.beans.factory.FactoryBean;

import java.util.Objects;

@Slf4j
public class IdServiceFactoryBean implements FactoryBean<IdService> {

    public enum Type{
        PROPERTY,IP_CONFIGURABLE,DB
    }

    private Type providerType;

    private long machineId;

    private String ips;

    private String dbURL;

    private String dbName;

    private String dbUser;

    private String dbPassword;

    private long genMethod = -1;

    private long type = -1;

    private long version = -1;

    private IdService idService;

    public void init(){
        if(Objects.isNull(providerType)){
            log.error("The type of Id service is mandatory.");
            throw new IllegalArgumentException(
                    "The type of Id service is mandatory.");
        }
        switch (providerType){
            case PROPERTY:
                idService = constructPropertyIdService();

        }
    }

    private IdService constructPropertyIdService() {
        return null;
    }


    @Override
    public IdService getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
