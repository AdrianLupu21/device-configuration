package com.smartmug.device.configuration.controller;

import java.text.MessageFormat;

public class DeviceConfigurationError {



    public DeviceConfigurationException newError(final DeviceConfigurationErrorCode deviceConfigurationErrorCode,
                                                 final String... errorArguments){
        return new DeviceConfigurationException(MessageFormat.format(deviceConfigurationErrorCode.getMessage(), errorArguments),
                deviceConfigurationErrorCode);
    }

    public static class DeviceConfigurationException extends RuntimeException{
        private final DeviceConfigurationErrorCode deviceConfigurationErrorCode;

        public DeviceConfigurationErrorCode getDeviceConfigurationErrorCode() {
            return deviceConfigurationErrorCode;
        }

        public DeviceConfigurationException(final String message,
                                            final DeviceConfigurationErrorCode deviceConfigurationErrorCode) {
            super(message);
            this.deviceConfigurationErrorCode = deviceConfigurationErrorCode;
        }
    }
}


