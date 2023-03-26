package com.smartmug.device.configuration.dto;

public class ResourceDTO {

    private byte[] resource;

    private String path;

    public byte[] getResource() {
        return resource;
    }

    public void setResource(final byte[] resource) {
        this.resource = resource;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }
}
