package com.smartmug.device.configuration.processor.impl;

import com.smartmug.device.configuration.controller.DeviceConfigurationError;
import com.smartmug.device.configuration.dao.DeviceConfigurationDAO;
import com.smartmug.device.configuration.dao.UserDAO;
import com.smartmug.device.configuration.dao.UserGroupDAO;
import com.smartmug.device.configuration.dao.ResourceDAO;
import com.smartmug.device.configuration.dto.DeviceConfigurationDTO;
import com.smartmug.device.configuration.dto.GroupDTO;
import com.smartmug.device.configuration.dto.UserDTO;
import com.smartmug.device.configuration.entities.DeviceConfigurationJpa;
import com.smartmug.device.configuration.entities.UserGroupJpa;
import com.smartmug.device.configuration.entities.UserJpa;
import com.smartmug.device.configuration.keycloak.messages.KeycloakErrorMessage;
import com.smartmug.device.configuration.processor.spi.DeviceConfigurationProcessor;
import com.smartmug.keycloak.KeycloakClient;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode.*;

@Component
//TODO add audit for actions
public class DeviceConfigurationProcessorImpl implements DeviceConfigurationProcessor {

    private static final String SEED = "jUB56i7V0xlGvcJ99Z3i";
    @Autowired
    ResourceDAO resourceDAO;

    @Autowired
    UserGroupDAO userGroupDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    KeycloakClient keycloakClient;

    @Autowired
    DeviceConfigurationDAO deviceConfigurationDAO;

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

    @Override
    public UserGroupJpa fetchGroupByName(final String name){
        return userGroupDAO.getGroupByName(name);
    }

    @Override
    public String getResource(final String resourcePath){
        final byte[] resourceByteArray = resourceDAO.getResource(resourcePath);
        return new String(resourceByteArray, StandardCharsets.UTF_8);
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
    public void registerDevice(DeviceConfigurationDTO deviceConfigurationDTO) {
        try {
            final String encryptedId = encryptAES(deviceConfigurationDTO.getDeviceId());
            if(deviceConfigurationDTO.getToken().equals(
                    encryptedId)){
                final DeviceConfigurationJpa deviceConfigurationJpa =
                        generateDeviceConfigurationJpa(encryptedId);
                deviceConfigurationDAO.registerDevice(deviceConfigurationJpa);
            }else{
                throw new DeviceConfigurationError().newError(INVALID_TOKEN);
            }
        } catch (Exception e) {
            throw new DeviceConfigurationError().newError(PROCESSING_EXCEPTION);
        }
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

    private UserRepresentation transformUserDTOtoUserRepresentation(final UserDTO userDTO){
        final UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setEmailVerified(false);
        CredentialRepresentation credentialRep = new CredentialRepresentation();
        credentialRep.setType(CredentialRepresentation.PASSWORD);
        credentialRep.setValue(userDTO.getPassword());
        credentialRep.setTemporary(false);
        user.setCredentials(Collections.singletonList(credentialRep));
        return user;
    }

    private DeviceConfigurationJpa generateDeviceConfigurationJpa(final String token){
        final DeviceConfigurationJpa deviceConfigurationJpa = new DeviceConfigurationJpa();
        deviceConfigurationJpa.setDeviceToken(token);
        return deviceConfigurationJpa;
    }

    private String encryptAES(String input) throws Exception {
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        byte[] seedBytes = SEED.getBytes(StandardCharsets.UTF_8);

        // Generate a 16-byte key using the seed bytes
        byte[] keyBytes = new byte[16];
        System.arraycopy(seedBytes, 0, keyBytes, 0, Math.min(seedBytes.length, keyBytes.length));
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // Initialize the cipher for encryption using the key and IV
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // Encrypt the input bytes and encode the result as base64
        byte[] encryptedBytes = cipher.doFinal(inputBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private void validateUser(final UserDTO userDTO){
        if(null == userDTO.getUsername()){
            throw new DeviceConfigurationError().newError(INVALID_TOKEN);
        }
        if(null == userDTO.getEmail()){
            throw new DeviceConfigurationError().newError(INVALID_TOKEN);
        }
    }
}
