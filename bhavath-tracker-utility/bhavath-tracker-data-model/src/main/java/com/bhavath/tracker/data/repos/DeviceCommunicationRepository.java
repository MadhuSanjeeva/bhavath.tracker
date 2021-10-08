package com.bhavath.tracker.data.repos;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.DeviceCommunication;

public interface DeviceCommunicationRepository extends TableRepository<DeviceCommunication, Long>, JpaSpecificationExecutor<DeviceCommunication> 
{
	@Query("FROM DeviceCommunication WHERE imeiNumber=:imeiNumber")
	public DeviceCommunication getByImeiNumber(String imeiNumber);
	
	public List<DeviceCommunication> findByUpdatedDateBeforeAndStatusEquals(Timestamp updatedDate, String status);
	
}
