package com.smartmug.device.resource;


import javax.validation.Valid;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.smartmug.device.configuration.dto.DeviceConfigurationDTO;
import com.smartmug.device.configuration.dto.GroupDTO;
import com.smartmug.device.configuration.dto.UserDTO;
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

    @PostMapping("/resource/{resourcePath}")
    Response addResource(@RequestBody final byte[] resource,@PathVariable final String resourcePath);

    @GetMapping("/resource/{resourcePath}")
    Response getResource(@PathVariable final String resourcePath);

    @PostMapping("/device")
    Response registerDevice(@RequestBody final DeviceConfigurationDTO deviceConfigurationDTO);
}
