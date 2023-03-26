package com.smartmug.device.configuration.dao;

import com.smartmug.device.configuration.dto.GroupDTO;
import com.smartmug.device.configuration.entities.UserGroupJpa;
import com.smartmug.device.configuration.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserGroupDAO {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public void insertGroup(final GroupDTO groupDTO){
        final UserGroupJpa userGroupJpa = new UserGroupJpa();
        userGroupJpa.setGroupName(groupDTO.getGroupName());
        userGroupRepository.save(userGroupJpa);
    }

    public UserGroupJpa getGroupByName(final String name){
        return userGroupRepository.findUserGroupByName(name);
    }

    public List<GroupDTO> getGroups(){
        return transformUserGroupJpaToGroupDTO(userGroupRepository.findAll());
    }

    private List<GroupDTO> transformUserGroupJpaToGroupDTO(List<UserGroupJpa> userGroupEntities){
        return userGroupEntities.stream().map(userGroupJpa -> {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setGroupName(userGroupJpa.getGroupName());
            return groupDTO;
        }).collect(Collectors.toList());
    }
}
