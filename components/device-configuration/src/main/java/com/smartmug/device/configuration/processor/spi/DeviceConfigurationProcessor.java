package com.smartmug.device.configuration.processor.spi;


import com.smartmug.device.configuration.dto.GroupDTO;
import com.smartmug.device.configuration.dto.UserDTO;
import com.smartmug.device.configuration.entities.UserGroupJpa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceConfigurationProcessor {

    void insertResource(final byte[] resource, final String path, final String username);

    void registerUser(final UserDTO userDTO);

    GroupDTO registerGroup(final GroupDTO groupDTO);

    UserDTO updateUser(final UserDTO userDTO);

    List<UserDTO> getUsersByGroupName(final String group);

    long removeUser(final long userId);

    List<GroupDTO> fetchGroups();

    UserGroupJpa fetchGroupByName(final String name);
}
