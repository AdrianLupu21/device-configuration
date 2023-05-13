package com.smartmug.device.configuration.resource;


import javax.validation.Valid;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.smartmug.device.configuration.dto.DeviceConfigurationDTO;
import com.smartmug.device.configuration.dto.GroupDTO;
import com.smartmug.device.configuration.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface DeviceConfigurationResource {

    @PostMapping("/user")
    Response addUser(@Valid @RequestBody final UserDTO userDTO);

    @GetMapping("/users")
    Response getUsers();

    @PostMapping("/group")
    Response addGroup(@RequestBody final GroupDTO userDTO);

    @GetMapping("/group/list")
    Response getGroups();

    @GetMapping("/group/{name}")
    Response getGroupByName(@PathVariable final String name);

    @GetMapping("users/{groupName}")
    Response getUserByGroupName(@PathVariable final String groupName);

    @PostMapping("/resource")
    Response addResource(@RequestBody final byte[] resource,@PathParam("resourcePath") final String resourcePath);

    @GetMapping("/resource")
    ResponseEntity<String> getResource(@PathParam("resourcePath") final String resourcePath);

    @PostMapping("/device")
    Response registerDevice(@RequestBody final DeviceConfigurationDTO deviceConfigurationDTO);
}
