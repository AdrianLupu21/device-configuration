package com.smartmug.device.management.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DeviceConnectionProperties {
    @NotEmpty
    private String deviceId;
    private int partitionId;

    @NotNull
    @Valid
    private DeviceGroupProperties deviceGroupProperties;
    @NotEmpty
    private String deviceType;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }

    public DeviceGroupProperties getDeviceGroupProperties() {
        return deviceGroupProperties;
    }

    public void setDeviceGroupProperties(DeviceGroupProperties deviceGroupProperties) {
        this.deviceGroupProperties = deviceGroupProperties;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
