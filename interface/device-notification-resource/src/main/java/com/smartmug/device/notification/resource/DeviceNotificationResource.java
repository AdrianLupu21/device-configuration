package com.smartmug.device.notification.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.core.Response;


public interface DeviceNotificationResource {

    @PostMapping("/template/update/{deviceId}")
    Response postTemplateUpdateNotification(@RequestBody final byte[] template,
                                            @PathVariable final String deviceId);

    @GetMapping("/template")
    ResponseEntity<byte[]> fetchTemplate();

    @PostMapping("/message")
    Response sendDataToDevice(final byte[] data);
}
