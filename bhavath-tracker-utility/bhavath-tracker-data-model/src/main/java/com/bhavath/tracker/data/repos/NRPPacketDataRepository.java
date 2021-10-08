package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.NRPPacketData;

public interface NRPPacketDataRepository extends TableRepository<NRPPacketData, Long>, JpaSpecificationExecutor<NRPPacketData> 
{

	@Query("FROM NRPPacketData where imei=:imei")
	public NRPPacketData getByImei(String imei);

	@Query("FROM NRPPacketData where vehicleRegnNo=:vehicleRegnNo")
	public NRPPacketData getByVehicleRegnNo(String vehicleRegnNo);

}
