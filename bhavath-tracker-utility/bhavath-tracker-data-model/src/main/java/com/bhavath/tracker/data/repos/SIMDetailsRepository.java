package com.bhavath.tracker.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.DeviceDetails;
import com.bhavath.tracker.data.model.SIMDetails;

public interface SIMDetailsRepository extends TableRepository<SIMDetails, Long>, JpaSpecificationExecutor<SIMDetails> 
{
	@Query("FROM SIMDetails where deviceDetails=:deviceDetails")
	public List<SIMDetails> getDeviceDetails(DeviceDetails deviceDetails);
	
	@Query("FROM SIMDetails WHERE mobileNumber=:mobileNumber")
	public SIMDetails getSIMByMobileNumber(String mobileNumber);
	
	public SIMDetails findByImsiNumber(String imsiNumber);
	
}
