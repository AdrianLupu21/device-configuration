package com.smartmug.device.configuration.controller;

import com.smartmug.device.configuration.dto.GroupDTO;
import com.smartmug.device.configuration.processor.spi.DeviceConfigurationProcessor;
import com.smartmug.device.resource.DeviceConfigurationResource;
import com.smartmug.device.configuration.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

//TODO add security with keycloak
@RestController
public class DeviceConfigurationController implements DeviceConfigurationResource {
    Logger logger = LoggerFactory.getLogger(DeviceConfigurationController.class);

    @Autowired
    DeviceConfigurationProcessor deviceConfigurationProcessor;

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response addGroup(final GroupDTO groupDTO) {
        logger.info("running `addGroup`");
        deviceConfigurationProcessor.registerGroup(groupDTO);
        return Response.ok(groupDTO).build();
    }

    @Override
    @PermitAll
    public Response addUser(UserDTO userDTO) {
        logger.info("running `addUser`");
        deviceConfigurationProcessor.registerUser(userDTO);
        return Response.ok(userDTO).build();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response getUsers(){
        logger.info("running `getUser`");
        return Response.ok(Collections.singletonList("test")).build();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response getGroups(){
        logger.info("running `getGroups`");
        return Response.ok(deviceConfigurationProcessor.fetchGroups()).build();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response getGroupByName(final String name){
        logger.info("running `getGroupByName`");
        return Response.ok(deviceConfigurationProcessor.fetchGroupByName(name)).build();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response getUserByGroupName(final String groupName){
        logger.info("running `getUserByGroupName`");
        final List<UserDTO> users = deviceConfigurationProcessor.getUsersByGroupName(groupName);
        return Response.ok(users).build();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response addResource(final byte[] resource,final String resourcePath) {
        logger.info("running `addResource`");
        final String username = "dummy";
        deviceConfigurationProcessor.insertResource(resource, resourcePath, username);
        return Response.ok().build();
    }
}
