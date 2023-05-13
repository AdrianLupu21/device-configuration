package com.smartmug.device.configuration.client.impl;

import com.smartmug.keycloak.KeycloakClient;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class ClientFactory {

    public static final String BASE_URL = "http://0.0.0.0:8090/maxcup/v1";

    @Autowired
    KeycloakClient keycloakClient;

    public KeycloakClient getKeycloakClient() {
        return keycloakClient;
    }

    public Client getClient(){
        AccessTokenResponse tokenResponse = keycloakClient.getKeycloakClient().tokenManager().getAccessToken();
        String token = tokenResponse.getToken();
        return ClientBuilder.newClient()
                .register(new Authenticator(token));

    }
}
