package com.smartmug.device.message;

import com.smartmug.device.message.error.ErrorMessage;

import java.util.Collections;
import java.util.List;

public class DiagnosticsMessage {
    private int cpuTemperature;
    private int batteryLevel;
    private int powerConsumption;
    private int MemoryUsage;
    private int cpuUsage;
    private int networkTraffic;
    private int ioOperations;
    private List<ErrorMessage> errorMessages;

    public int getCpuTemperature() {
        return cpuTemperature;
    }

    public void setCpuTemperature(int cpuTemperature) {
        this.cpuTemperature = cpuTemperature;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public int getMemoryUsage() {
        return MemoryUsage;
    }

    public void setMemoryUsage(int memoryUsage) {
        MemoryUsage = memoryUsage;
    }

    public int getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(int cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public int getNetworkTraffic() {
        return networkTraffic;
    }

    public void setNetworkTraffic(int networkTraffic) {
        this.networkTraffic = networkTraffic;
    }

    public int getIoOperations() {
        return ioOperations;
    }

    public void setIoOperations(int ioOperations) {
        this.ioOperations = ioOperations;
    }

    public List<ErrorMessage> getErrorMessages() {
        return Collections.unmodifiableList(errorMessages);
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
