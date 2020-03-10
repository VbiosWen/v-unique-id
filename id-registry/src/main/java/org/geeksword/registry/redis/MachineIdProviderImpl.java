package org.geeksword.registry.redis;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.geeksword.registry.utils.IpUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@NoArgsConstructor
public class MachineIdProviderImpl implements MachineIdProvider, InitializingBean {

    private static final String MACHINE_ID_KEY = "machine_id";

    private static final ConcurrentHashMap<String, Integer> MACHINE_IP_ID = new ConcurrentHashMap<>();

    private static final int MAX_MACHINE_SIZE = 1024;

    private Long machineId = 0L;

    private String machineIp = null;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


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

        if (CollectionUtils.isEmpty(members)) {
            log.error("get machine info from redis is null");
            throw new IllegalStateException("get machine info from redis is null");
        }

        if (members.size() > MAX_MACHINE_SIZE) {
            log.error("machine size > {}", MAX_MACHINE_SIZE);
            throw new IllegalStateException("machine size > " + MAX_MACHINE_SIZE);
        }

        int index = 0;
        for (Object member : members) {
            String ip = String.valueOf(member);
            MACHINE_IP_ID.put(ip, index);
            if (machineIp.equals(ip)) {
                machineId = (long) index;
            }
            index++;
        }
        if (log.isInfoEnabled()) {
            log.info("current machine ip is :{}, machine id is:{}", machineIp, machineId);
        }
    }
}
