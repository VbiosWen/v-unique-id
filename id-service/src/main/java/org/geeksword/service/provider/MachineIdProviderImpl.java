package org.geeksword.service.provider;

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
