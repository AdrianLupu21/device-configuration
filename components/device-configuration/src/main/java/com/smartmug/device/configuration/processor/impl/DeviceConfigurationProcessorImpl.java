package com.smartmug.device.configuration.processor.impl;

import com.smartmug.device.configuration.controller.DeviceConfigurationError;
import com.smartmug.device.configuration.controller.KeycloakClient;
import com.smartmug.device.configuration.dao.UserDAO;
import com.smartmug.device.configuration.dao.UserGroupDAO;
import com.smartmug.device.configuration.dao.ResourceDAO;
import com.smartmug.device.configuration.dto.GroupDTO;
import com.smartmug.device.configuration.dto.UserDTO;
import com.smartmug.device.configuration.entities.UserGroupJpa;
import com.smartmug.device.configuration.entities.UserJpa;
import com.smartmug.device.configuration.keycloak.messages.KeycloakErrorMessage;
import com.smartmug.device.configuration.processor.spi.DeviceConfigurationProcessor;
import com.smartmug.device.configuration.repository.UserRepository;
import org.apache.catalina.User;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode.CANNOT_REGISTER_USER;
import static com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode.REGISTER_CONFLICT_ERROR;

@Component
//TODO add audit for actions
public class DeviceConfigurationProcessorImpl implements DeviceConfigurationProcessor {

    @Autowired
    ResourceDAO resourceDAO;

    @Autowired
    UserGroupDAO userGroupDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    KeycloakClient keycloakClient;

    @Override
    public void insertResource(byte[] resource, String path, String username) {
        resourceDAO.insertResource(resource, path, username);
    }

    @Override
    public void registerUser(final UserDTO userDTO) {
        UserRepresentation user = transformUserDTOtoUserRepresentation(userDTO);
        RealmResource realmResource = keycloakClient.getKeycloakClient().realm("test");
        UsersResource usersResource = realmResource.users();
        try(Response response = usersResource.create(user)) {
           if(response.getStatus() == Response.Status.CONFLICT.getStatusCode()){
               KeycloakErrorMessage error = response.readEntity(KeycloakErrorMessage.class);
                throw new DeviceConfigurationError().newError(REGISTER_CONFLICT_ERROR, userDTO.getUsername(),
                        error.getErrorMessage());
           }else if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
               throw new DeviceConfigurationError().newError(CANNOT_REGISTER_USER, userDTO.getUsername());
           }
        }catch (DeviceConfigurationError.DeviceConfigurationException e){
            throw e;
        }catch (Exception e){
            throw new DeviceConfigurationError().newError(CANNOT_REGISTER_USER, userDTO.getUsername());
        }
        userDAO.insertUser(userDTO);
    }

    private UserRepresentation transformUserDTOtoUserRepresentation(final UserDTO userDTO){
        final UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        CredentialRepresentation credentialRep = new CredentialRepresentation();
        credentialRep.setType(CredentialRepresentation.PASSWORD);
        credentialRep.setValue(userDTO.getPassword());
        credentialRep.setTemporary(false);
        user.setCredentials(Collections.singletonList(credentialRep));
        return user;
    }

    @Override
    public UserGroupJpa fetchGroupByName(final String name){
        return userGroupDAO.getGroupByName(name);
    }

    @Override
    public GroupDTO registerGroup(GroupDTO groupDTO) {
        userGroupDAO.insertGroup(groupDTO);
        return groupDTO;
    }

    @Override
    public List<GroupDTO> fetchGroups(){
        return userGroupDAO.getGroups();
    }

    @Override
    public UserDTO updateUser(final UserDTO userDTO) {
        return null;
    }

    @Override
    public List<UserDTO> getUsersByGroupName(final String groupName){
        final UserGroupJpa userGroupJpa = userGroupDAO.getGroupByName(groupName);
        return userGroupJpa.getUsers().stream().map(
                this::transformUserJpaToUserDTO).collect(Collectors.toList());
    }

    @Override
    public long removeUser(long userId) {
        return 0;
    }

    private UserDTO transformUserJpaToUserDTO(final UserJpa userJpa){
        final UserDTO userDTO = new UserDTO();
        userDTO.setGroupName(userJpa.getGroup().getGroupName());
        userDTO.setEmail(userJpa.getEmail());
        userDTO.setFirstName(userJpa.getFirstName());
        userDTO.setLastName(userJpa.getLastName());
        userDTO.setUsername(userJpa.getUsername());
        return userDTO;
    }
}
