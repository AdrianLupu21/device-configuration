package com.smartmug.device.configuration.controller;

public enum DeviceConfigurationErrorCode {
    DUPLICATE_USER("Multiple users with the username {0}, found inside the database"),
    USERNAME_TAKEN("Username {0} is already assigned"),
    INVALID_GROUP_NAME("Group with name {0} was not found"),
    CANNOT_REGISTER_USER("User {0} could not be registered in keycloak"),
    INVALID_TOKEN("The token provided is not valid"),
    PROCESSING_EXCEPTION("Error while processing the hash of the id"),
    REGISTER_CONFLICT_ERROR("User {0} could not be registered in keycloak because: {1}");
    private final String message;

    public String getMessage() {
        return message;
    }

    DeviceConfigurationErrorCode(final String message){
        this.message = message;
    }
}
