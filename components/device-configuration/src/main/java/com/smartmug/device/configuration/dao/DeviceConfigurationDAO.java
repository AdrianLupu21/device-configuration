package com.smartmug.device.configuration.dao;

import com.smartmug.device.configuration.entities.DeviceConfigurationJpa;
import com.smartmug.device.configuration.repository.DeviceConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceConfigurationDAO {

    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    public void registerDevice(final DeviceConfigurationJpa deviceConfigurationJpa){
        deviceConfigurationRepository.save(deviceConfigurationJpa);
    }

    public DeviceConfigurationJpa findDeviceByToken(final String token){
        return deviceConfigurationRepository.findDeviceByToken(token);
    }
}
