package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.HMPPacketData;

public interface HMPPacketDataRepository extends TableRepository<HMPPacketData, Long>, JpaSpecificationExecutor<HMPPacketData> 
{

	@Query("FROM HMPPacketData where imeiNumber=:imeiNumber")
	public HMPPacketData getByImei(String imeiNumber);
}
