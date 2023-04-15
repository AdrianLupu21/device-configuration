package com.smartmug.device.configuration.controller;

import com.smartmug.device.configuration.dto.DeviceConfigurationErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.smartmug.device.configuration.controller.DeviceConfigurationErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        final StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(fieldName)
                    .append(":")
                    .append(message)
                    .append(System.lineSeparator());
        });
        final DeviceConfigurationErrorDTO deviceConfigurationErrorDTO = new DeviceConfigurationErrorDTO();
        deviceConfigurationErrorDTO.setMessage(errorMessage.toString());
        deviceConfigurationErrorDTO.setCode(BAD_REQUEST.toString());
        deviceConfigurationErrorDTO.setTimestamp(Instant.now().toString());
        return new ResponseEntity<>(deviceConfigurationErrorDTO, HttpStatus.BAD_REQUEST);
    }

    public Map<DeviceConfigurationErrorCode, HttpStatus> mapErrorCodes(){
        final Map<DeviceConfigurationErrorCode, HttpStatus> errors = new HashMap<>();
        errors.put(DUPLICATE_USER, BAD_REQUEST);
        errors.put(INVALID_GROUP_NAME, BAD_REQUEST);
        errors.put(USERNAME_TAKEN, BAD_REQUEST);
        errors.put(CANNOT_REGISTER_USER, BAD_REQUEST);
        errors.put(REGISTER_CONFLICT_ERROR, BAD_REQUEST);
        errors.put(INVALID_TOKEN, BAD_REQUEST);
        errors.put(PROCESSING_EXCEPTION, INTERNAL_SERVER_ERROR);
        return errors;
    }
}
