package com.smartmug.device.configuration.repository;

import com.smartmug.device.configuration.entities.UserGroupJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupJpa, Integer> {

    @Query("SELECT ug FROM com.smartmug.device.configuration.entities.UserGroupJpa ug WHERE ug.groupName= :name")
    UserGroupJpa findUserGroupByName(final String name);
}
