package com.smartmug.device.management.resource;

import com.smartmug.device.management.dto.DeviceConnectionProperties;
import com.smartmug.device.management.dto.DeviceGroupProperties;
import com.smartmug.device.management.dto.DeviceResourceProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;


public interface DeviceManagementResource {

    @GetMapping("device/{deviceId}")
    ResponseEntity<DeviceConnectionProperties> getDeviceConnectionProperties(@PathVariable final String deviceId);

    @GetMapping("device/{deviceId}/{resourceType}")
    ResponseEntity<DeviceResourceProperties> getDeviceResourceProperties(@PathVariable final String deviceId,
                                                         @PathVariable final String resourceType);

    @PostMapping("device")
    Response registerDevice(@Valid @RequestBody final DeviceConnectionProperties deviceConnectionProperties);

    @PostMapping("device/resource")
    Response mapResourceToDevice(@Valid @RequestBody final DeviceResourceProperties deviceResourceProperties);

    @PutMapping("device/{deviceId}")
    ResponseEntity<DeviceResourceProperties> updateDeviceResource(@Valid @RequestBody final DeviceResourceProperties deviceResourceProperties);

    @PostMapping("group")
    Response registerGroup(@Valid @RequestBody final DeviceGroupProperties deviceGroupProperties);

    @PutMapping("group")
    ResponseEntity<DeviceGroupProperties> updateGroup(@Valid @RequestBody final DeviceGroupProperties deviceGroupProperties);
}
