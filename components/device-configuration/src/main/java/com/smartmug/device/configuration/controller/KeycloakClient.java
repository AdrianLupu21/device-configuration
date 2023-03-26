package com.smartmug.device.configuration.controller;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.ClientBuilder;

@Component
public class KeycloakClient {

    private Keycloak keycloakClient;

    @Autowired
    private KeycloakSpringBootProperties keycloakProperties;

    @PostConstruct
    private void init(){
        keycloakClient = KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getAuthServerUrl())
                .realm(keycloakProperties.getRealm())
                .clientId("admin-cli")
                .clientSecret("71cb4b28-b6b7-44b7-b36e-3cf394f15042")
                .grantType("client_credentials")
                .build();
    }

    public String getAccessToken(){
        AccessTokenResponse tokenResponse = keycloakClient.tokenManager().getAccessToken();
        return tokenResponse.getToken();
    }

    public Keycloak getKeycloakClient() {
        return keycloakClient;
    }
}
