package com.bhavath.tracker.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.RawPacketData;

public interface RawPacketDataRepository  extends TableRepository<RawPacketData, Long>, JpaSpecificationExecutor<RawPacketData> 
{

	@Query("FROM RawPacketData WHERE serialNumber=:serialNumber and packetDate=:packetDate")
	List<RawPacketData> getDataBySerialNumberAndDate(String serialNumber, String packetDate);

}

