package com.smartmug.device.management.dto;

import javax.validation.constraints.NotEmpty;

public class DeviceResourceProperties {
    @NotEmpty
    private String deviceId;
    @NotEmpty
    private String resourcePath;
    @NotEmpty
    private String resourceType;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
