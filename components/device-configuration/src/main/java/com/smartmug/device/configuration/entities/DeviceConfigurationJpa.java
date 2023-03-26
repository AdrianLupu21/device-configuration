package com.smartmug.device.configuration.entities;

import javax.persistence.*;

@Entity(name="device_configuration")
public class DeviceConfigurationJpa {
    @Id
    @JoinColumn(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_token")
    private String deviceToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
