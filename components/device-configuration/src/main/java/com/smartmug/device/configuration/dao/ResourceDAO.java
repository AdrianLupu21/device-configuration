package com.smartmug.device.configuration.dao;

import com.smartmug.device.configuration.entities.ResourceRepositoryJpa;
import com.smartmug.device.configuration.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class ResourceDAO {

    @Autowired
    private ResourceRepository resourceRepository;

    public void insertResource(final byte[] resource, final String path, final String username){
        final ResourceRepositoryJpa resourceRepositoryJpa = new ResourceRepositoryJpa();
        resourceRepositoryJpa.setResource(resource);
        resourceRepositoryJpa.setDateTime(OffsetDateTime.now());
        resourceRepositoryJpa.setPath(path);
        resourceRepositoryJpa.setUpdatedBy(username);
        resourceRepository.save(resourceRepositoryJpa);
    }
}
