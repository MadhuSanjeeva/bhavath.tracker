package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.HealthPacketData;

public interface HealthPacketDataRepository extends TableRepository<HealthPacketData, Long>, JpaSpecificationExecutor<HealthPacketData> 
{

	@Query("FROM HealthPacketData where imeiNumber=:imeiNumber")
	public HealthPacketData getByImei(String imeiNumber);
}
