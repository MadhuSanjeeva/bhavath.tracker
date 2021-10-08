package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.LMPPacketData;

public interface LMPPacketDataRepository extends TableRepository<LMPPacketData, Long>, JpaSpecificationExecutor<LMPPacketData> 
{

	@Query("FROM LMPPacketData where imei=:imei")
	public LMPPacketData getByImei(String imei);

	@Query("FROM LMPPacketData where vehicleRegNo=:vehicleRegNo")
	public LMPPacketData getByVehicleRegNo(String vehicleRegNo);

}
