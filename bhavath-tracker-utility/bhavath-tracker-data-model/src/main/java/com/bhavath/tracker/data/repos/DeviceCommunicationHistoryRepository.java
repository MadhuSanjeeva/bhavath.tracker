package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.DeviceCommunicationHistory;

public interface DeviceCommunicationHistoryRepository extends TableRepository<DeviceCommunicationHistory, Long>, JpaSpecificationExecutor<DeviceCommunicationHistory> 
{
	@Query("FROM DeviceCommunicationHistory WHERE serialNumber=:serialNumber")
	public DeviceCommunicationHistory getBySerialNumber(String serialNumber);

}
