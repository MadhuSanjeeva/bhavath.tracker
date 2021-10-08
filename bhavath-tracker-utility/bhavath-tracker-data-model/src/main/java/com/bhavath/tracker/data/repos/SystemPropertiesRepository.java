package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bhavath.tracker.data.model.SystemProperties;

@Repository
public interface SystemPropertiesRepository extends TableRepository<SystemProperties, Long>, JpaSpecificationExecutor<SystemProperties>{

	public SystemProperties findByPropertyName(String propertyName);
	
}
