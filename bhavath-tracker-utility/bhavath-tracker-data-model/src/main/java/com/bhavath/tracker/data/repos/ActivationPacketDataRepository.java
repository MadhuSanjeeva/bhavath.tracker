package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.ActivationPacketData;

public interface ActivationPacketDataRepository extends TableRepository<ActivationPacketData, Long>, JpaSpecificationExecutor<ActivationPacketData> 
{

	@Query("FROM ActivationPacketData where imeiNumber=:imeiNumber")
	public ActivationPacketData getByImei(String imeiNumber);
}
