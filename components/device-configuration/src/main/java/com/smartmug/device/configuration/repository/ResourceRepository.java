package com.smartmug.device.configuration.repository;

import com.smartmug.device.configuration.entities.ResourceRepositoryJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceRepositoryJpa, Integer> {

}
