package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bhavath.tracker.data.model.VehicleDetails;

public interface VehicleDetailsRepository extends TableRepository<VehicleDetails, Long>, JpaSpecificationExecutor<VehicleDetails> {

	public VehicleDetails findByVehicleRegNumber(String vehicleRegNumber);
	
	public VehicleDetails findByImeiNumber(String imeiNumber);
	
	public VehicleDetails findByChassisNumber(String chassisNumber);
	
	public VehicleDetails findByEngineNumber(String engineNumber);
	
}
