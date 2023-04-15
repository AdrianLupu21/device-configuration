package com.smartmug.keycloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KeycloakClient {

    private Keycloak keycloakClient;

    @Autowired
    private Environment environment;

    @Autowired
    private KeycloakSpringBootProperties keycloakProperties;

    @PostConstruct
    private void init(){
        keycloakClient = KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getAuthServerUrl())
                .realm(keycloakProperties.getRealm())
                .clientId(environment.getProperty("connector.resource"))
                .clientSecret(environment.getProperty("connector.credentials.secret"))
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
