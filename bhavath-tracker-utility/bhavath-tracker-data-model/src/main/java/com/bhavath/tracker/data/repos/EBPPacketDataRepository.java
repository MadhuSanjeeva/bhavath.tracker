package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.EBPPacketData;

public interface EBPPacketDataRepository extends TableRepository<EBPPacketData, Long>, JpaSpecificationExecutor<EBPPacketData> 
{

	@Query("FROM EBPPacketData where imei=:imei")
	public EBPPacketData getByImei(String imei);

	@Query("FROM EBPPacketData where vehicleRegnNo=:vehicleRegnNo")
	public EBPPacketData getByVehicleRegnNo(String vehicleRegnNo);

}
