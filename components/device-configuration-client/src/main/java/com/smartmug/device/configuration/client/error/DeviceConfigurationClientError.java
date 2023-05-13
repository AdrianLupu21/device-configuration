package com.smartmug.device.configuration.client.error;


public class DeviceConfigurationClientError {

    public static DeviceConfigurationClientException newError(final String responseMessage, final int responseStatus){
        return new DeviceConfigurationClientException(responseMessage, responseStatus);
    }

    public static class DeviceConfigurationClientException extends RuntimeException{
        private int responseStatus;

        public DeviceConfigurationClientException(String message, int responseStatus) {
            super(message);
            this.responseStatus = responseStatus;
        }

        public int getResponseStatus() {
            return responseStatus;
        }
    }
}
