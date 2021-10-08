package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bhavath.tracker.data.model.Roles;

public interface RolesRepositary extends TableRepository<Roles, Long>, JpaSpecificationExecutor<Roles>
{

}
