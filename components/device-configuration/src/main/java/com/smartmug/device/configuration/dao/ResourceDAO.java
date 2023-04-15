package com.smartmug.device.configuration.dao;

import com.smartmug.device.configuration.entities.ResourceRepositoryJpa;
import com.smartmug.device.configuration.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Component
public class ResourceDAO {

    @Autowired
    private ResourceRepository resourceRepository;

    public void insertResource(final byte[] resource, final String path, final String username){
        final ResourceRepositoryJpa resourceRepositoryJpa = new ResourceRepositoryJpa();
        resourceRepositoryJpa.setResource(resource);
        resourceRepositoryJpa.setLastUpdateDate(OffsetDateTime.now());
        resourceRepositoryJpa.setPath(path);
        resourceRepositoryJpa.setUpdatedBy(username);
        resourceRepository.save(resourceRepositoryJpa);
    }

    public byte[] getResource(final String resourcePath){
        final ResourceRepositoryJpa resourceRepositoryJpa = resourceRepository.findByResourcePath(resourcePath);

        return resourceRepositoryJpa.getResource();
    }
}
