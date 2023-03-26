package com.smartmug.device.configuration.controller;

import com.smartmug.device.configuration.dto.DeviceConfigurationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class DeviceConfigurationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeviceConfigurationError.DeviceConfigurationException.class)
    public ResponseEntity<DeviceConfigurationErrorDTO> handleException(
        final DeviceConfigurationError.DeviceConfigurationException deviceConfigurationException){
        final Map<DeviceConfigurationErrorCode, HttpStatus> errorCodes = mapErrorCodes();
        DeviceConfigurationErrorDTO deviceConfigurationErrorDTO = new DeviceConfigurationErrorDTO();
        deviceConfigurationErrorDTO.setCode(deviceConfigurationException.getDeviceConfigurationErrorCode().name());
        deviceConfigurationErrorDTO.setMessage(deviceConfigurationException.getMessage());
        deviceConfigurationErrorDTO.setTimestamp(Instant.now().toString());
        return new ResponseEntity<>(deviceConfigurationErrorDTO,
                errorCodes.get(deviceConfigurationException.getDeviceConfigurationErrorCode()));
    }

    public Map<DeviceConfigurationErrorCode, HttpStatus> mapErrorCodes(){
        final Map<DeviceConfigurationErrorCode, HttpStatus> errors = new HashMap<>();
        errors.put(DUPLICATE_USER, BAD_REQUEST);
        errors.put(INVALID_GROUP_NAME, BAD_REQUEST);
        errors.put(USERNAME_TAKEN, BAD_REQUEST);
        errors.put(CANNOT_REGISTER_USER, BAD_REQUEST);
        errors.put(REGISTER_CONFLICT_ERROR, BAD_REQUEST);
        return errors;
    }
}
