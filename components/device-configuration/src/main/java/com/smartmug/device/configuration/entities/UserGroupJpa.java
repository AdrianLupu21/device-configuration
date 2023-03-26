package com.smartmug.device.configuration.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name="user_group")
public class UserGroupJpa {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="group_name")
    private String groupName;

    @OneToMany(mappedBy = "id")
    private List<UserJpa> users;

    public List<UserJpa> getUsers() {
        return users;
    }

    public void setUsers(List<UserJpa> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
