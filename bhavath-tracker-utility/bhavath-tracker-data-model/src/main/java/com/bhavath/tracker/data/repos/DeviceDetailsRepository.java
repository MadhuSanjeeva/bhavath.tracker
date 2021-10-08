package com.bhavath.tracker.data.repos;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.DeviceDetails;
import com.bhavath.tracker.data.model.SIMDetails;

public interface DeviceDetailsRepository extends TableRepository<DeviceDetails, Long>, JpaSpecificationExecutor<DeviceDetails> 
{

	public DeviceDetails findByImeiNumber(String imeiNumber);

	@Query("FROM DeviceDetails WHERE serialNumber=:serialNumber")
	public DeviceDetails findBySerialNumber(String serialNumber);
	
	@Query("FROM DeviceDetails WHERE imsiNumber=:imsiNumber")
	public DeviceDetails findByIMSINumber(String imsiNumber);
	
	@Query("FROM SIMDetails where simDetails=:simDetails")
	public Set<SIMDetails> getSIMDetails(SIMDetails simDetails);

}
