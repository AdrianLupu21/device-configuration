package com.smartmug.device.configuration.client.impl;

import com.smartmug.device.configuration.client.error.DeviceConfigurationClientError;
import com.smartmug.device.configuration.client.spi.DeviceConfigurationClient;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.net.URISyntaxException;

import static com.smartmug.device.configuration.client.impl.ClientFactory.BASE_URL;

@Service
public class DeviceConfigurationClientImpl implements DeviceConfigurationClient {

    @Autowired
    ClientFactory clientFactory;

    @Override
    public String getResource(final String resourcePath) throws URISyntaxException {
        final URIBuilder builder = new URIBuilder(BASE_URL + File.separator + "resource")
                .addParameter("resourcePath", resourcePath);
        final String requestPath = builder.build().toString();
        final Client client = clientFactory.getClient();
        final Response response = client.target(requestPath).request(MediaType.APPLICATION_JSON_TYPE).get();
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            return response.readEntity(String.class);
        }else{
            throw DeviceConfigurationClientError.newError(response.getStatusInfo().getReasonPhrase(),
                    response.getStatus());
        }
    }

    @Override
    public void insertResource(final String resourcePath, final byte[] resourceTemplate) throws URISyntaxException {
        final URIBuilder builder = new URIBuilder(BASE_URL + File.separator + "resource")
                .addParameter("resourcePath", resourcePath);
        final String requestPath = builder.build().toString();
        final Client client = clientFactory.getClient();
        Entity<byte[]> entity = Entity.entity(resourceTemplate, MediaType.APPLICATION_OCTET_STREAM);
        try(final Response response = client.target(requestPath)
                .request(MediaType.APPLICATION_JSON_TYPE).post(entity)){
            if(response.getStatus() != Response.Status.OK.getStatusCode()){
                throw DeviceConfigurationClientError.newError(response.getStatusInfo().getReasonPhrase(),
                        response.getStatus());
            }
        }
    }
}
