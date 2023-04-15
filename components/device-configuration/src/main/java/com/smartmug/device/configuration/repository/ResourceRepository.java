package com.smartmug.device.configuration.repository;

import com.smartmug.device.configuration.entities.ResourceRepositoryJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceRepositoryJpa, Integer> {
    @Query("SELECT r FROM com.smartmug.device.configuration.entities.ResourceRepositoryJpa r WHERE r.path= :resourcePath")

    ResourceRepositoryJpa findByResourcePath(final String resourcePath);
}
