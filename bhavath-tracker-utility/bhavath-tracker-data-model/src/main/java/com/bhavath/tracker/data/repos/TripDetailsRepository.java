package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bhavath.tracker.data.model.TripDetails;

public interface TripDetailsRepository extends TableRepository<TripDetails, Long>, JpaSpecificationExecutor<TripDetails> 
{
	public TripDetails findByImeiNumber(String imeiNumber);
	
	@Query("FROM TripDetails where imeiNumber=:imeiNumber and isTripClosed=false")
	public TripDetails getTripByIMEINumberAndStatus(@Param("imeiNumber") String imeiNumber);
}
