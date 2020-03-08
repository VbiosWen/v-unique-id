package org.geeksword.service.provider;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MachineIdProviderImpl implements MachineIdProvider {
    private long machineId;



    @Override
    public long getMachineId() {
        return this.machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
