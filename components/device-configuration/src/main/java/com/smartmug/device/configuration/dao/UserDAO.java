package com.smartmug.device.configuration.dao;

import com.smartmug.device.configuration.controller.DeviceConfigurationError;
import com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode;
import com.smartmug.device.configuration.dto.UserDTO;
import com.smartmug.device.configuration.entities.UserGroupJpa;
import com.smartmug.device.configuration.entities.UserJpa;
import com.smartmug.device.configuration.repository.UserGroupRepository;
import com.smartmug.device.configuration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode.INVALID_GROUP_NAME;
import static com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode.USERNAME_TAKEN;

@Component
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public void insertUser(final UserDTO userDTO){
        UserGroupJpa userGroupJpa = userGroupRepository.findUserGroupByName(userDTO.getGroupName());
        validateUserEntry(userDTO, userGroupJpa);
        userRepository.save(transformUserDTOToUser(userDTO, userGroupJpa));
    }
    private UserJpa transformUserDTOToUser(final UserDTO userDTO, final UserGroupJpa userGroupJpa){
        final UserJpa user = new UserJpa();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setGroup(userGroupJpa);
        user.setUsername(userDTO.getUsername());
        return user;
    }

    private void validateUserEntry(final UserDTO userDTO, final UserGroupJpa userGroupJpa){
        if(null != userRepository.findByUsername(userDTO.getUsername())){
            throw new DeviceConfigurationError().newError(USERNAME_TAKEN, userDTO.getUsername());
        }
        if(null == userGroupJpa){
            throw new DeviceConfigurationError().newError(INVALID_GROUP_NAME, userDTO.getGroupName());
        }
    }
}
