package org.geekswrod.registry.redis;

import lombok.extern.slf4j.Slf4j;
import org.geekswrod.registry.redis.utils.IpUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MachineIdProviderImpl implements MachineIdProvider, InitializingBean {

    private static final String MACHINE_ID_KEY = "machine_id";

    private static final ConcurrentHashMap<String, Integer> MACHINE_IP_ID = new ConcurrentHashMap<>();

    private Long machineId = 0L;

    private String machineIp = null;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public long getMachineId() {
        return machineId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        machineIp = IpUtils.getIp0();
        if (StringUtils.isEmpty(machineIp)) {
            log.error("get ip error");
            throw new IllegalStateException("get ip error");
        }
        if (!redisTemplate.hasKey(MACHINE_ID_KEY)) {
            redisTemplate.opsForSet().add(MACHINE_ID_KEY, machineIp);
        }

        Boolean contains = redisTemplate.opsForSet().isMember(MACHINE_ID_KEY, machineIp);
        if (Objects.isNull(contains)) {
            log.error("judge machine ip error:{}", machineIp);
            throw new IllegalStateException("judge machine ip error:" + machineIp);
        }
        if (!contains) {
            redisTemplate.opsForSet().add(MACHINE_ID_KEY, machineIp);
        }

        Set members = redisTemplate.opsForSet().members(MACHINE_ID_KEY);

        int index = 0;
        for (Object member : members) {
            String ip = String.valueOf(member);
            MACHINE_IP_ID.put(ip, index);
            if (machineIp.equals(ip)) {
                machineId = (long) index;
            }
            index++;
        }
    }
}
