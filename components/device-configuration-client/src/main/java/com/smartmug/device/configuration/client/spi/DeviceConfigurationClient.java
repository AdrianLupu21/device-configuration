package com.smartmug.device.configuration.client.spi;

import java.net.URISyntaxException;

public interface DeviceConfigurationClient {

    /**
     * Fetch template file from the database based on its path
     *
     * @param resourcePath
     * @return byte array of template file
     */
    String getResource(final String resourcePath) throws URISyntaxException;

    /** Insert resource template file into the database
     * @param resourcePath path of the file
     * @param resourceTemplate byte array containing data of the file
     */
    void insertResource(final String resourcePath, final byte[] resourceTemplate) throws URISyntaxException;
}
