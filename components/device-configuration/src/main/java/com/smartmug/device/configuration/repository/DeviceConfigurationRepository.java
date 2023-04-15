package com.smartmug.device.configuration.repository;

import com.smartmug.device.configuration.entities.DeviceConfigurationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceConfigurationRepository extends JpaRepository<DeviceConfigurationJpa, Integer> {

    @Query("SELECT d FROM com.smartmug.device.configuration.entities.DeviceConfigurationJpa d WHERE d.deviceToken= :token")
    DeviceConfigurationJpa findDeviceByToken(final String token);
}
