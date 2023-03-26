package com.smartmug.device.configuration.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name="user_evidence")
public class UserJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    @JoinColumn(name = "group_id", nullable = false)
    @ManyToOne()
    private UserGroupJpa userGroupJpa;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name="device_id")
    private List<DeviceConfigurationJpa> deviceConfigurationJpa;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGroupJpa getGroup() {
        return userGroupJpa;
    }

    public void setGroup(UserGroupJpa group) {
        this.userGroupJpa = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DeviceConfigurationJpa> getDeviceConfigurationJpa() {
        return deviceConfigurationJpa;
    }

    public void setDeviceConfigurationJpa(List<DeviceConfigurationJpa> deviceConfigurationJpa) {
        this.deviceConfigurationJpa = deviceConfigurationJpa;
    }
}
