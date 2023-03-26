package com.smartmug.device.configuration.repository;

import com.smartmug.device.configuration.entities.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJpa, Integer> {

    @Query("SELECT u FROM com.smartmug.device.configuration.entities.UserJpa u WHERE u.username = :username")
    UserJpa findByUsername(final String username);
}
