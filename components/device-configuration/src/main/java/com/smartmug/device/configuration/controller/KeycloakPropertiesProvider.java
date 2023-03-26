package com.smartmug.device.configuration.controller;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class KeycloakPropertiesProvider implements ApplicationListener<ContextRefreshedEvent> {

    private String serverUrl;
    private String realm;
    private String clientId;


    @Autowired
    private KeycloakSpringBootProperties keycloakProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        serverUrl = keycloakProperties.getAuthServerUrl();
        realm = keycloakProperties.getRealm();
        clientId = keycloakProperties.getResource();
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getRealm() {
        return realm;
    }

    public String getClientId() {
        return clientId;
    }
}
